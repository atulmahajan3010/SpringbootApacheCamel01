package com.demo.SpringbootApacheCamel01;

import java.util.StringTokenizer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRouterNew extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		//To consume files from source to destination
		consumeFile();
	}

	
	private void consumeFile() {
		from("{{my.app.source}}")
		.process(
				(exchange) -> {
				
				// read input message given by source
				Message input = exchange.getIn();
				
				//Read body as a string from input message
				String data = input.getBody(String.class);
				
				//operation
				StringTokenizer str = new StringTokenizer(data, ",");
				String eid = str.nextToken();
				String ename = str.nextToken();
				String esal =  str.nextToken();
				
				// output message
				String dataModified = "{eid:"+eid+",ename:"+ename+",esal:"+esal+"}";
				
				//read output message reference
				Message output = exchange.getMessage();
				
				//Set data to output
				output.setBody(dataModified);
				
			}
		)
			// Changing File name
			.to("{{my.app.destination}}?fileName=emp.json");
	}
}
