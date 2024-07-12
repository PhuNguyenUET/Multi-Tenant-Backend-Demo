package com.example.Demo_MultiTenant_MongoDB.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    String companyName;

}
