package com.example.webbanhang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.response.DienthoaiResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongtinphanloai;
import com.example.webbanhang.mapper.DienthoaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThongtinphanloaiRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DienthoaiService {
    private final DienthoaiRepository dienthoaiRepository;
    private final DienthoaiMapper dienthoaiMapper;
    private final ThongtinphanloaiRepository thongtinphanloaiRepository;

    @Transactional
    public DienthoaiResponse createDienthoai(DienthoaiRequest request) {
        Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai());

        if (dienthoaiRepository.findByTensanpham(request.getTensanpham()) != null) {
            throw new IllegalArgumentException("Dien thoai đã tồn tại");
        }

        if (thongtinphanloai == null) {
            throw new IllegalArgumentException("Danh muc not found");
        }

        Dienthoai dienthoai = Dienthoai.builder()
                .tensanpham(request.getTensanpham())
                .hinhanh(request.getHinhanh())
                .hinhanhduyet(request.getHinhanhduyet())
                .bonho(request.getBonho())
                .ram(request.getRam())
                .giaban(request.getGiaban())
                .thongtinphanloai(thongtinphanloai)
                .build();

        Dienthoai savedDienthoai = dienthoaiRepository.save(dienthoai);
        return dienthoaiMapper.toDienthoaiResponse(savedDienthoai);
    }

    @Transactional
    public DienthoaiResponse updateDienthoai(Long id, DienthoaiRequest request) {
        Dienthoai dienthoai = dienthoaiRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("không tồn tại not found"));

        if (request.getTenphanloai() != null && !request.getTenphanloai().isEmpty()) {
            Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai());
            if (thongtinphanloai == null) {
                throw new IllegalArgumentException("Danh muc not found");
            }
            dienthoai.setThongtinphanloai(thongtinphanloai);
        }

        if (request.getTensanpham() != null && !request.getTensanpham().isEmpty()) {
            dienthoai.setTensanpham(request.getTensanpham());
        }
        if (request.getRam() != null && !request.getRam().isEmpty()) {
            dienthoai.setRam(request.getRam());
        }
        if (request.getBonho() != null && !request.getBonho().isEmpty()) {
            dienthoai.setBonho(request.getBonho());
        }
        if (request.getGiaban() != null && !request.getGiaban().isEmpty()) {
            dienthoai.setGiaban(request.getGiaban());
        }
        if (request.getHinhanh() != null && !request.getHinhanh().isEmpty()) {
            dienthoai.setHinhanh(request.getHinhanh());
        }
        if (request.getHinhanhduyet() != null && !request.getHinhanhduyet().isEmpty()) {
            dienthoai.setHinhanhduyet(request.getHinhanhduyet());
        }

        Dienthoai updatedDienthoai = dienthoaiRepository.save(dienthoai);
        return dienthoaiMapper.toDienthoaiResponse(updatedDienthoai);
    }

    public List<Map<String, Object>> getPhoneProductsWithRandomColor() {
        List<Object[]> results = dienthoaiRepository.findPhoneProductsWithRandomColor();
        List<Map<String, Object>> phones = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> phone = new HashMap<>();
            phone.put("id", result[0]);
            phone.put("tensanpham", result[1]);
            phone.put("tenmausac", result[2]);
            phone.put("hinhanh", result[3]);
            phone.put("giaban", result[4]); // Bao gồm giá bán ở đây
            phones.add(phone);
        }

        return phones;
    }

    @Transactional
    public List<Map<String, Object>> getPhoneProductsWithRandomColor1() {
        List<Object[]> results = dienthoaiRepository.findPhoneProductsWithRandomColor1();
        return convertToMap(results);
    }

    //    @Transactional
    //    public List<Map<String, Object>> getPhoneProductsWithFilters(
    //            List<String> ram, String hedieuhanh, String boNho, Long giaTu, Long giaDen) {
    //        List<Object[]> results;
    //        if ((ram == null || ram.isEmpty()) && hedieuhanh == null && boNho == null && giaTu == null && giaDen ==
    // null) {
    //            results = dienthoaiRepository.findPhoneProductsWithRandomColor();
    //        } else {
    //            results = dienthoaiRepository.findPhoneProductsWithFilters(ram, hedieuhanh, boNho, giaTu, giaDen);
    //        }
    //        return convertToMap(results);
    //    }

    private final JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getPhoneProductsWithFilters(
            List<String> ram, List<String> hedieuhanh, List<String> boNho, Long giaTu, Long giaDen) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban ");
        sql.append("FROM webbanhang.dienthoai dt ");
        sql.append("JOIN ( ");
        sql.append("    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban ");
        sql.append("    FROM webbanhang.mausac m ");
        sql.append("    JOIN ( ");
        sql.append("        SELECT dienthoai_id, MIN(id) AS id ");
        sql.append("        FROM webbanhang.mausac ");
        sql.append("        GROUP BY dienthoai_id ");
        sql.append("        ORDER BY RAND() ");
        sql.append("    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id ");
        sql.append(") ms ON dt.id = ms.dienthoai_id ");
        sql.append("JOIN webbanhang.thongtinphanloai ttpl ON dt.thongtinphanloai_id = ttpl.id ");
        sql.append("JOIN webbanhang.loaisanpham lsp ON ttpl.loaisanpham_id = lsp.id ");
        sql.append("JOIN webbanhang.danhmuc dm ON lsp.danhmuc_id = dm.id ");
        sql.append("JOIN webbanhang.hedieuhanh hd ON dm.hedieuhanh_id = hd.id ");
        sql.append("WHERE 1=1 ");

        List<Object> parameters = new ArrayList<>();

        if (ram != null && !ram.isEmpty()) {
            sql.append("AND dt.ram IN (");
            for (int i = 0; i < ram.size(); i++) {
                sql.append("?");
                if (i < ram.size() - 1) sql.append(", ");
                parameters.add(ram.get(i));
            }
            sql.append(") ");
        }

        if (hedieuhanh != null && !hedieuhanh.isEmpty()) {
            sql.append("AND hd.tenhedieuhanh IN (");
            for (int i = 0; i < hedieuhanh.size(); i++) {
                sql.append("?");
                if (i < hedieuhanh.size() - 1) sql.append(", ");
                parameters.add(hedieuhanh.get(i));
            }
            sql.append(") ");
        }

        if (boNho != null && !boNho.isEmpty()) {
            sql.append("AND dt.bonho IN (");
            for (int i = 0; i < boNho.size(); i++) {
                sql.append("?");
                if (i < boNho.size() - 1) sql.append(", ");
                parameters.add(boNho.get(i));
            }
            sql.append(") ");
        }

        if (giaTu != null && giaDen != null) {
            sql.append("AND ms.giaban BETWEEN ? AND ? ");
            parameters.add(giaTu);
            parameters.add(giaDen);
        }

        return jdbcTemplate.queryForList(sql.toString(), parameters.toArray());
    }

    private List<Map<String, Object>> convertToMap(List<Object[]> results) {
        List<Map<String, Object>> phones = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> phone = new HashMap<>();
            phone.put("id", result[0]);
            phone.put("tensanpham", result[1]);
            phone.put("tenmausac", result[2]);
            phone.put("hinhanh", result[3]);
            phone.put("giaban", result[4]);
            phones.add(phone);
        }
        return phones;
    }

    //    private List<Map<String, Object>> convertToMap(List<Object[]> results) {
    //        List<Map<String, Object>> phones = new ArrayList<>();
    //        for (Object[] result : results) {
    //            Map<String, Object> phone = new HashMap<>();
    //            phone.put("id", result[0]);
    //            phone.put("tensanpham", result[1]);
    //            phone.put("tenmausac", result[2]);
    //            phone.put("hinhanh", result[3]);
    //            phone.put("giaban", result[4]);
    //            phones.add(phone);
    //        }
    //        return phones;
    //    }
}
