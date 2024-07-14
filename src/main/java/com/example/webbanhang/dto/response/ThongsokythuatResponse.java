package com.example.webbanhang.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ThongsokythuatResponse {
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
    private String chatlieukhungvien;
    private String chisokhangnuocbui;
    private String kieumanhinh;
    private String cambienvantai;
    private String cacloaicambien;
    private String tinhnangdacbiet;
    private String dacdiennoibat;
    private String chitiet;
    private Long dienthoaiId;
}
