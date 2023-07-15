package net.blog.springbootrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// lambook anotation to avoid boiler plate code like constructor,getter,setter
@Data
@AllArgsConstructor
@NoArgsConstructor
//entity is used to match this to sql database @table is used to specify the table name and other unique contraints if any
@Entity
@Table(
        name="posts",uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    //id and genarated value is used to specify its primary key and generated value to automate
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="content", nullable = false)
    private String content;
}
