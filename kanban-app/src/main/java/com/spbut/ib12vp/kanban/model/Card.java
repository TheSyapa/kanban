package com.spbut.ib12vp.kanban.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spbut.ib12vp.kanban.model.auth.Dev;
import com.spbut.ib12vp.kanban.model.enums.Direction;
import com.spbut.ib12vp.kanban.model.enums.TaskStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@Table(name = "card")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String name;

    @ApiModelProperty(position = 3)
    private String type;

    @ManyToOne(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @ApiModelProperty(position = 4)
    private Dev author;

    @ManyToOne(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "worker_id")
    @ApiModelProperty(position = 5)
    private Dev worker;

    @ManyToOne(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "tester_id")
    @ApiModelProperty(position = 6)
    private Dev tester;

    @ApiModelProperty(position = 7)
    private Timestamp timeCreated;

    @ApiModelProperty(position = 8)
    private Timestamp timeTaken;

    @ApiModelProperty(position = 9)
    private Timestamp timeDeadline;

    @ApiModelProperty(position = 10)
    private Timestamp timeFactEndTime;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(position = 11)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(position = 12)
    private Direction direction;

    @ApiModelProperty(position = 13)
    private String color;

    @ApiModelProperty(position = 14)
    private String description;
}
