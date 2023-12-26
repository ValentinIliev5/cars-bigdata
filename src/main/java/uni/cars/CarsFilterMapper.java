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

public class CarsFilterMapper extends MapReduceBase 
	implements Mapper<LongWritable,Text,Text,DoubleWritable>{

	String brandNameT,minHPT,maxHPT,MPGT;
	
	@Override
	public void configure(JobConf job) {
		brandNameT = job.get("brandName", "");
		minHPT = job.get("minHP", "");
		maxHPT = job.get("maxHP", "");
		MPGT = job.get("MPG", "");
	}

	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter)
			throws IOException {
		
		double minHP = 0;
		double maxHP = 10000;
		double MPG = 0 ;
		
		if(!minHPT.isEmpty()) {
			minHP = Double.parseDouble(minHPT);
		}
		if(!maxHPT.isEmpty()) {
			maxHP = Double.parseDouble(maxHPT);
		}
		if(!MPGT.isEmpty()) {
			MPG = Double.parseDouble(MPGT);
		}
		
		String[] columns = value.toString().split(";");
		
		try {
			if(columns[0].toLowerCase().contains(brandNameT.toLowerCase())&&
					Double.parseDouble(columns[5])>=minHP&&
					Double.parseDouble(columns[5])<=maxHP&&
					Double.parseDouble(columns[2])>=MPG
					)
			{
			output.collect(new Text(columns[0] + " " + columns[1] + " " + columns[5] + " " + columns[9]),
					new DoubleWritable(Double.parseDouble(columns[2])));
			}
		}catch(NumberFormatException ex)
		{
			System.err.println(value.toString() + " - " + columns[0]);
			System.err.println(ex.getMessage());
		}
	}
}
