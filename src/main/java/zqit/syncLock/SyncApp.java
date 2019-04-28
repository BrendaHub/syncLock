package zqit.syncLock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@ComponentScan
public class SyncApp  extends SpringBootServletInitializer //需要打包为war时， 继承SpringBootServletInitializer，同时重写configure方法
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SyncApp.class);
    }
	
    public static void main( String[] args )
    {
    	SpringApplication.run(SyncApp.class, args);
    }

}