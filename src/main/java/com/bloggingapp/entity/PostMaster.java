package com.bloggingapp.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bloggingapp.dto.CommentDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;

	@Column(name = "post_title", length = 100, nullable = false)
	private String title;

	private String content;

	private String imageName;

	private Date addDate;

	@ManyToOne
	private UserMaster user;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryMaster category;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<CommentMaster> comment = new HashSet<>();

}
