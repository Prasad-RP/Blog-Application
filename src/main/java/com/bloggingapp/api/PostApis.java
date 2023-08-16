package com.bloggingapp.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bloggingapp.config.AppConstant;
import com.bloggingapp.dto.ApiResponse;
import com.bloggingapp.dto.PostDto;
import com.bloggingapp.dto.PostResponse;
import com.bloggingapp.services.FileServices;
import com.bloggingapp.services.PostServices;

import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Prasad Pansare
 * 
 */
@RestController
@RequestMapping("/api/post")
public class PostApis {

	private static final Logger log = LoggerFactory.getLogger(PostApis.class);

	@Autowired
	private PostServices services;

	@Autowired
	private FileServices fileServices;

	@Value("${project.image}")
	String path;

	// addPost
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		log.info("addig post with category id" + categoryId + " for user with id" + userId);
		PostDto dto = null;
		try {
			dto = this.services.createPost(postDto, userId, categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PostDto>(dto, HttpStatus.CREATED);
	}

	// updatePost
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@PathVariable Integer id, @RequestBody PostDto postDto) {
		log.info("Updating post with id:" + id);

		PostDto dto = null;
		try {
			dto = this.services.updatePost(postDto, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PostDto>(dto, HttpStatus.ACCEPTED);
	}

	// delete post
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id) {
		log.info("Deleting post with id:" + id);
		try {
			this.services.deletePost(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(new ApiResponse("User deleted successfully ...", true), HttpStatus.OK);
	}

	// get single post
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getSinglePost(@PathVariable Integer id) {
		log.info("Getting post with id:" + id);
		return ResponseEntity.ok(this.services.getSinglePost(id));
	}

	// get all post
	@GetMapping("/allposts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIRETION, required = false) String sortDir) {
		log.info("Getting all posts ");
		PostResponse allPost = null;
		try {
			allPost = this.services.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}

	// get all post by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategogy(@PathVariable Integer categoryId) {
		log.info("Getting all posts by category id:" + categoryId);
		List<PostDto> post = null;
		try {
			post = this.services.getPostsByCategory(categoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<PostDto>>(post, HttpStatus.OK);

	}

	// get all post by user
	@GetMapping("/{postId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer postId) {
		log.info("Getting all posts by Post id:" + postId);
		List<PostDto> post = null;
		try {
			post = this.services.getPostByUser(postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<PostDto>>(post, HttpStatus.OK);
	}

	// search by title
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords) {
		List<PostDto> post = null;
		post = this.services.serchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(post, HttpStatus.OK);
	}

	// post image upload
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId)
			throws IOException {
		PostDto postDto = null;
		String fileName = "";
		PostDto updatePost = null;
		try {
			postDto = this.services.getSinglePost(postId);
			fileName = this.fileServices.uploadImage(path, image);
			postDto.setImageName(fileName);
			updatePost = this.services.updatePost(postDto, postId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	// method to serve files
	@GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		InputStream resource = null;
		resource = this.fileServices.getResource(path, imageName);
		try {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
