package com.bloggingapp.servicesimpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bloggingapp.dto.PostDto;
import com.bloggingapp.dto.PostResponse;
import com.bloggingapp.entity.CategoryMaster;
import com.bloggingapp.entity.PostMaster;
import com.bloggingapp.entity.UserMaster;
import com.bloggingapp.exception.ResourceNotFoundException;
import com.bloggingapp.repository.CategoryRepository;
import com.bloggingapp.repository.PostRepository;
import com.bloggingapp.repository.UserRepository;
import com.bloggingapp.services.PostServices;
import com.bloggingapp.utility.GlobleResources;

@Service
public class PostServicesImpl implements PostServices {
	
	private Logger logger=GlobleResources.getLogger(PostServicesImpl.class);

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private ModelMapper modelmapper;

	@Override
	public PostDto createPost(PostDto postdto,Integer userId,Integer categoryId) {
		logger.info("Sarted createPost() Function");
		UserMaster user=null;
		CategoryMaster category =null;
		PostMaster post=null;
		PostMaster savePost=null;
		
		try {
			user= this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User" , "Id", userId));
			category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
			
			post=this.modelmapper.map(postdto, PostMaster.class);
			
			post.setImageName("default.png");
			post.setAddDate(new Date());
			post.setUser(user);
			post.setCategory(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		savePost=this.postRepo.save(post);
		
		return this.modelmapper.map(savePost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer id) {
		logger.info("Sarted updatePost() Function");
		PostMaster post=null;
		PostMaster save=null;
		try {

			post=this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post ", " id ", id));
			post.setTitle(postDto.getTitle());
			post.setContent(postDto.getContent());
			post.setImageName(postDto.getImageName());
			save= this.postRepo.save(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.modelmapper.map(save, PostDto.class);
	}

	@Override
	public void deletePost(Integer id) {
		logger.info("Sarted deletePost() Function");
		PostMaster post=null;
		try {
			post=this.postRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException(" Post ", " id ", id));

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.postRepo.delete(post);
		
		
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageValue,String sortBy,String sortDir) {
		logger.info("Sarted getAllPOst() Function");
		Sort sort=null;
		Page<PostMaster> pagePost=null;
		List<PostMaster> allPost=null;
		List<PostDto> userDto=null;
		PostResponse post=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		
		Pageable p=PageRequest.of(pageNumber, pageValue,sort );
	
		
		//List<Post> allPost=this.postRepo.findAll();
		
		try {
			pagePost=this.postRepo.findAll(p);
			allPost=pagePost.getContent();
			
			userDto = allPost.stream().map(post1 -> this.modelmapper.map(post1,PostDto.class)).collect(Collectors.toList());	
			
			post= new PostResponse();		
			post.setContent(userDto);
			post.setPageNumber(pagePost.getNumber());
			post.setPageSize(pagePost.getSize());
			post.setTotalElement(pagePost.getTotalElements());
			post.setTotalPage(pagePost.getTotalPages());
			post.setLastPage(pagePost.isLast());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return post;
	}

	@Override
	public PostDto getSinglePost(Integer id) {
		logger.info("Sarted getSinglePost() Function");
		PostMaster post=null;
		
		try {
			post=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post ", " id ", id));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.modelmapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		logger.info("Sarted getPostByCategory() Function");
		CategoryMaster categary=null;
		List<PostMaster> posts=null;
		List<PostDto> postDto=null;
		
		try {
			categary= this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", " id ", categoryId));
			posts=this.postRepo.findByCategory(categary);
			postDto=posts.stream().map((post)-> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return postDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		logger.info("Sarted getPostByUser() Function");
		UserMaster user=null;
		List<PostMaster> post=null;
		List<PostDto> postDto=null;
		try {
			user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", " id ", userId));
			post=this.postRepo.findByUser(user);
			postDto=post.stream().map((p)->this.modelmapper.map(p, PostDto.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return postDto;
	}
	
	
	@Override
	public List<PostDto> serchPosts(String keyword) {
		logger.info("Sarted serchPosts() Function");
		List<PostMaster> post=null;
		List<PostDto> postDto=null;
		
		try {
			post=this.postRepo.findByTitleContaining(keyword);	
			postDto=post.stream().map((p)->this.modelmapper.map(p, PostDto.class)).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return postDto;
	}


}
