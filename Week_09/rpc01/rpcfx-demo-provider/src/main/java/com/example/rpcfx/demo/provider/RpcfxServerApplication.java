package com.example.rpcfx.demo.provider;

import com.example.rpcfx.api.RpcfxRequest;
import com.example.rpcfx.api.RpcfxResolver;
import com.example.rpcfx.api.RpcfxResponse;
import com.example.rpcfx.demo.api.OrderService;
import com.example.rpcfx.demo.api.UserService;
import com.example.rpcfx.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RpcfxServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RpcfxServerApplication.class, args);
	}

	@Autowired
    RpcfxInvoker invoker;

	@PostMapping("/")
	public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
		return invoker.invoke(request);
	}

	@Bean
	public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver){
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public RpcfxResolver createResolver(){
		return new DemoResolver();
	}

	// 能否去掉name
	//
//	@Bean(name = "UserService")
	@Bean
	public UserService createUserService(){
		return new UserServiceImpl();
	}

//	@Bean(name = "OrderService")
    @Bean
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
