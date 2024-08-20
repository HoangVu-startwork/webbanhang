package com.example.webbanhang.entity;

import jakarta.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Thongsokythuat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kichthuocmanhinh;
    private String congnghemanghinh;
    private String tinhnangmanghinh;
    private String tansoquet;
    private String camerasau;
    private String quayvideo;
    private String tinhnagcamera;
    private String cameratruoc;
    private String quayvideotruoc;
    private String loaicpu;
    private String dophangiai;
    private String chipset;
    private String gpu;
    private String khecamthenho;
    private String pin;
    private String congnghesac;
    private String congsac;
    private String thesim;
    private String hedieuhang;
    private String hongngoai;
    private String jacktainghe;
    private String congghenfc;
    private String hotromang;
    private String wifi;
    private String bluetooth;
    private String gps;
    private String kichthuoc;
    private String trongluong;
    private String chatlieumatlung;
    private String tuongthich;
    private String chatlieukhungvien;
    private String chisokhangnuocbui;
    private String kieumanhinh;
    private String cambienvantai;
    private String cacloaicambien;
    private String tinhnangdacbiet;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String dacdiennoibat;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String chitiet;

    @ManyToOne
    @JoinColumn(name = "dienthoai_id")
    private Dienthoai dienthoai;
}
