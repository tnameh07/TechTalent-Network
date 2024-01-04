package project.techTalent.Network.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="posts")
public class Post
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String postId;
	@Column(name="post_title",length=100,nullable= false)
	private String title;
	@Column(length=100)
	private String content;
	private String imageName;
	private Date addedDate;
	
	@ManyToOne
	private Category category;
	
	@ManyToOne
	private User user;

}
