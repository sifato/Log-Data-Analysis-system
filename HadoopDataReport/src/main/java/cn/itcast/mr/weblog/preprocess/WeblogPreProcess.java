package cn.itcast.mr.weblog.preprocess;

import cn.itcast.mr.weblog.bean.WebLogBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 处理原始日志，过滤出真实请求数据，转换时间格式，对缺失字段填充默认值，对记录标记valid和invalid
 */
public class WeblogPreProcess {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(WeblogPreProcess.class);
		job.setMapperClass(WeblogPreProcessMapper.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		FileInputFormat.setInputPaths(job, new Path("d:/weblog/input"));
		FileOutputFormat.setOutputPath(job, new Path("d:/weblog/output"));
		job.setNumReduceTasks(0);
		boolean res = job.waitForCompletion(true);
		System.exit(res ? 0 : 1);
	}

	public static class WeblogPreProcessMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
		// 用来存储网站url分类数据
		Set<String> pages = new HashSet<String>();
		Text k = new Text();
		NullWritable v = NullWritable.get();
		/**
		 * 设置初始化方法，加载网站需要分析的url分类数据，存储到MapTask的内存中，用来对日志数据进行过滤
		 * 如果用户请求的资源是以下列形式，就表示用户请求的是合法资源。
		 */
		@Override
		protected void setup(Context context) throws IOException, InterruptedException {
			pages.add("/about");
			pages.add("/black-ip-list/");
			pages.add("/cassandra-clustor/");
			pages.add("/finance-rhive-repurchase/");
			pages.add("/hadoop-family-roadmap/");
			pages.add("/hadoop-hive-intro/");
			pages.add("/hadoop-zookeeper-intro/");
			pages.add("/hadoop-mahout-roadmap/");
		}

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			//获取一行数据
			String line = value.toString();
			//调用解析类WebLogParser解析日志数据，最后封装为WebLogBean对象
			WebLogBean webLogBean = WebLogParser.parser(line);
			if (webLogBean != null) {
				// 过滤js/图片/css等静态资源
				WebLogParser.filtStaticResource(webLogBean, pages);
				k.set(webLogBean.toString());
				context.write(k, v);
			}
		}
	}
}
