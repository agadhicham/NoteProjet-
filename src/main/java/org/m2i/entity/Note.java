package org.m2i.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Note implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long id;
	@Column(length=30)
	private String titre;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dateCreation;
	@Column(length=50)
	private String descreption;
	private String noty;
	private String photo;
	

}
