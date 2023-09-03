package com.sheryians.major.model;

import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

// FOR PRODUCT  PAGE

@Entity //will create a Table
@Data   //lombok getter setter etc...
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // long is primary key
    private String name; // product name


    //one category can have many PRODUCTS
    //JOIN columns of 2 table in JPA BY  @ManyToOne(fetch= FetchType.LAZY)
    // @JoinColumn (Product table column , Category Table column)
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="category_id", referencedColumnName = "category_id")
    private Category category;

    private double price;
    private double weight;
    private String description;
    private String imageName;// will save image in folder and save image name in db



}
