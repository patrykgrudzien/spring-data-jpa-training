package com.jurik99.jpa;

import lombok.Builder;
import lombok.Data;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import java.time.ZonedDateTime;

@Entity
@Table(name = "todos")
@Data
@Builder(builderMethodName = "Builder")
public class Todo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "creation_time", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime creationTime;

	@Column(name = "description", length = 500)
	private String description;

	@Column(name = "modification_time")
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime modificationTime;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "header", nullable = false, length = 50)
	private String header;

	@Version
	private long version;
}
