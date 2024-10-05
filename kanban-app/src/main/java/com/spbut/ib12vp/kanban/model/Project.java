package com.spbut.ib12vp.kanban.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spbut.ib12vp.kanban.model.auth.Dev;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "project")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Project.class)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ManyToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "dev_id")
    @Fetch(FetchMode.SELECT)
    @ApiModelProperty(position = 3)
    private List<Dev> devs;

    @OneToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    @Fetch(FetchMode.SELECT)
    @ApiModelProperty(position = 4)
    private List<Card> cards;


    public void addTask(Card card) {

        if (Objects.isNull(cards)) {
            cards = new ArrayList<>();
        }
        cards.add(card);
    }

    public void addDev(Card card) {

        if (Objects.isNull(cards)) {
            cards = new ArrayList<>();
        }
        cards.add(card);
    }
}
