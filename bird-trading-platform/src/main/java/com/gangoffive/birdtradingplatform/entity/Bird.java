package com.gangoffive.birdtradingplatform.entity;

import com.gangoffive.birdtradingplatform.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.gangoffive.birdtradingplatform.enums.Gender;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "tblBird")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@SQLDelete(sql = "update tbl_Bird set is_deleted = true where product_id = ?")
public class Bird extends Product {
    protected Integer age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    protected Gender gender;

    @Column(nullable = false)
    protected String color;

    @ManyToOne
    @JoinColumn(
            name = "type_id",
            foreignKey = @ForeignKey(name = "FK_BIRD_TYPE_BIRD")
    )
    private TypeBird typeBird;

    @ManyToMany(mappedBy = "birds")
    private List<Tag> tags = new ArrayList<>();

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TypeBird getTypeBird() {
        return typeBird;
    }

    public void setTypeBird(TypeBird typeBird) {
        this.typeBird = typeBird;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void addTags(Tag tag) {
        this.tags.add(tag);
    }
}
