package com.ltfullstack.bookservice.command.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Thực thể ánh xạ với bảng trong cơ sở dữ liệu
@Table(name = "books") // Tên bảng trong cơ sở dữ liệu
@Getter // Getter cho tất cả các trường
@Setter // Setter cho tất cả các trường
@NoArgsConstructor // Constructor không tham số
public class Book {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID) // Tự động sinh giá trị ID
    private String id;
    private String name;
    private String author;
    private Boolean isReady;
}