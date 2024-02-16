package com.Sumitav.Entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Content {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long conId;

	@Column(length = 2000)
    private String title;
    
    private String conImage;
    
    @Transient
    private MultipartFile conImageFile;
     
    @Column(length = 5000)
    private String  description;

    private boolean active = false;
    
    @Column(length=2000)
    private String link_url;
    
    private String cloudinaryPublicId;

     

	@ManyToOne(fetch = FetchType.EAGER)
    private Category category;
	
	@OneToMany(mappedBy = "content",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DetailedContent>  detailedContent= new HashSet<>();
	

	public Content() {

    }

    public Long getConId() {
        return conId;
    }

    public void setConId(Long conId) {
        this.conId = conId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

     

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public String getConImage() {
        return conImage;
    }

    public void setConImage(String conImage) {
        this.conImage = conImage;
    }

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	
	
	public String getCloudinaryPublicId() {
		return cloudinaryPublicId;
	}

	public void setCloudinaryPublicId(String cloudinaryPublicId) {
		this.cloudinaryPublicId = cloudinaryPublicId;
	}
    
	public Set<DetailedContent> getDetailedContent() {
		return detailedContent;
	}

	public void setDetailedContent(Set<DetailedContent> detailedContent) {
		this.detailedContent = detailedContent;
	}

	public MultipartFile getConImageFile() {
		return conImageFile;
	}

	public void setConImageFile(MultipartFile conImageFile) {
		this.conImageFile = conImageFile;
	}

     

}
