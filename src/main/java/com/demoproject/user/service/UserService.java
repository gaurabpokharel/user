package com.demoproject.user.service;

import com.demoproject.user.VO.Blog;
import com.demoproject.user.VO.ResponseTemplateVO;
import com.demoproject.user.entity.User;
import com.demoproject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;



    public User saveUser(User user) throws Exception {
        log.info("Inside the saveUser of UserService");
        return userRepository.save(user);
    }

    public User findUserById(long userId) {
        log.info("Inside findUserById of UserService");
        return userRepository.findByUserId(userId);
    }

    public ResponseTemplateVO getUserWithBlog(Long userId) {
        log.info("Inside getUserWithBlog of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);

        Blog blog =
                restTemplate.getForObject("http://BLOG-SERVICE/blogs/findBlogById/" + user.getBlogId(),
                        Blog.class);

        vo.setUser(user);
        vo.setBlog(blog);
        return vo;
    }
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }
}
