package hello.exception;

import hello.exception.filter.LogFilter;
import hello.exception.interceptor.LogInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1) // 필터 순서 지정
                .addPathPatterns("/**") // 모든 url에 적용
                // 제외할 url을 구체적으로 지정한다. 예) "/error-page/**"는 에러를 위한 재호출이므로 제외함.
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error-page/**");
    }

    //@Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1); // 필터 순서 지정
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 url에 적용
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR); // REQUEST, ERROR에만 적용

        return filterRegistrationBean;
    }
}
