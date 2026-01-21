package com.empresa.data.models;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@Table
public class CreditsModel implements Persistable<String> {
    @Id
    private String id;

    public CreditsModel() {
        this.isNew = true;
        this.id = UUID.randomUUID().toString();
    }

    @CreatedDate
    @Column(value = "created_at")
    private LocalDateTime createdAt;
    private String pointName;
    private String pointId;
    private String amount;
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
