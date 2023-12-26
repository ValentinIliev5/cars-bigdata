package uni.cars;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class CarsBrandMapper extends MapReduceBase 
	implements Mapper<LongWritable,Text,Text,DoubleWritable>{

	
	String brand;
	
	@Override
	public void configure(JobConf job) {
		brand = job.get("brandName", "");
	}
	
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter)
			throws IOException {
		
		String[] columns = value.toString().split(";");
		
		if(columns[0].toLowerCase().contains(brand.toLowerCase()))
		{
			try {
				double consumption = Double.parseDouble(columns[2].trim());
				
				output.collect(new Text(columns[0].trim()), new DoubleWritable(consumption));
				
			}catch(NumberFormatException ex) {
				System.err.println(value.toString() + " - " + columns[0]);
				System.err.println(ex.getMessage());
			}
		}
	}

}
