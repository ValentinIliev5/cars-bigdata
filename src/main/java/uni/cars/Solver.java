package uni.cars;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.RunningJob;

class Solver {
	static Configuration conf = new Configuration();
	
	static JobConf job = new JobConf(conf,Solver.class);
	
	static Path inputPath = new Path("hdfs://127.0.0.1:9000/input/cars.csv");
	static Path outputPath = new Path("hdfs://127.0.0.1:9000/CarsOutput");
	
	static void startAvgByBrand(String brandName) {
		
		FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);
		
		job.set("brandName", brandName);
		job.setMapperClass(CarsBrandMapper.class);
		job.setReducerClass(CarsBrandReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
        try {
			FileSystem fs = FileSystem.get(URI.create("hdfs://127.0.0.1:9000"),conf);
			
			if(fs.exists(outputPath)) {
				fs.delete(outputPath,true);
			}
			RunningJob task = JobClient.runJob(job);
			
			System.out.println("Is successful " + task.isSuccessful());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void startAvgByFilters(String brandName, String minHP, String maxHP, String MPG) {
		
		FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);
		
		job.set("brandName", brandName);
		job.set("minHP", minHP);
		job.set("maxHP", maxHP);
		job.set("MPG", MPG);
		
		
		job.setMapperClass(CarsFilterMapper.class);
		job.setReducerClass(CarsFilterReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		try {
			FileSystem fs = FileSystem.get(URI.create("hdfs://127.0.0.1:9000"),conf);
			
			if(fs.exists(outputPath)) {
				fs.delete(outputPath,true);
			}
			RunningJob task = JobClient.runJob(job);
			
			System.out.println("Is successful " + task.isSuccessful());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
