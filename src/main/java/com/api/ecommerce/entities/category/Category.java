package com.api.ecommerce.entities.category;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",length = 128, nullable = false, unique = true)
    private String name;

    @Column(name = "alias",length = 128, nullable = false, unique = true)
    private String alias;

    @Column(name = "image",length = 128, nullable = false)
    private String image;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("name asc")
    private Set<Category> children = new HashSet<>();
}
