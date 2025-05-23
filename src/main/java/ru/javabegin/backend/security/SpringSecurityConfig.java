package ru.javabegin.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // данный класс будет считан как конфиг для spring контейнера
@EnableWebSecurity(debug = true) // debug = true полезен при разработке для просмотра лога какие бины были созданы, в production нужно ставить false
public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL; // клиентский URL

    // настройки безопасности для цепочки фильтров
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(); // разрешает выполнять preflight запросы типа OPTIONS, чтобы они не блокировались и у них не проверялись токены
        // все сетевые настройки
        http.authorizeRequests()
                .anyRequest().permitAll() // остальной API будет доступен только аутентифицированным пользователям
                .and()
                .csrf().disable(); // отключаем встроенную защиту от CSRF атак, т.к. используем свою, из OAUTH2

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.
                        addMapping("/**"). // для всех URL
                        allowedOrigins(clientURL). // с каких адресов разрешать запросы (можно указывать через запятую)
                        allowCredentials(true). // разрешить отправлять куки для межсайтового запроса
                        allowedHeaders("*"). // разрешить все заголовки - без этой настройки в некоторых браузерах может не работать
                        allowedMethods("*"); // все методы разрешены (GET,POST и пр.) - без этой настройки CORS не будет работать!
            }
        };
    }
}