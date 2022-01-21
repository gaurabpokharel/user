package com.demoproject.user.service;

import com.demoproject.user.VO.Blog;
import com.demoproject.user.VO.ResponseTemplateVO;
import com.demoproject.user.entity.User;
import com.demoproject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) throws Exception {
        log.info("Inside the saveUser of UserService");
        User local=userRepository.findByUsername(user.getUsername());
        if(local !=null)
        {
            throw  new Exception("User already exist");
        }
        String newPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    public User findUserById(long userId) throws Exception {
        log.info("Inside findUserById of UserService");
        User object= userRepository.findByUserId(userId);
        if(object == null)
        {
            throw new Exception("User not found with the userId= "+userId);
        }
        return  object;

    }

    public ResponseTemplateVO getUserWithBlog(Long userId) throws Exception {
        log.info("Inside getUserWithBlog of UserService");
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = userRepository.findByUserId(userId);
        if(user == null)
        {
            throw new Exception("User not found with the userId= "+userId);
        }

        Blog blog =
                restTemplate.getForObject("http://BLOG-SERVICE/blogs/findBlogById/" + user.getBlogId(),
                        Blog.class);

        vo.setUser(user);
        vo.setBlog(blog);
        return vo;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return user;
    }
}
