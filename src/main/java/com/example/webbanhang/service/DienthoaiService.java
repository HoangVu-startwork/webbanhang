package com.example.webbanhang.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.request.SoSanhDienThoaiResponse;
import com.example.webbanhang.dto.response.*;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongsokythuat;
import com.example.webbanhang.entity.Thongtinphanloai;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.DienthoaiMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThongtinphanloaiRepository;
import com.example.webbanhang.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class DienthoaiService {
    DienthoaiRepository dienthoaiRepository;
    DienthoaiMapper dienthoaiMapper;
    ThongtinphanloaiRepository thongtinphanloaiRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Transactional
    public DienthoaiResponse createDienthoai(DienthoaiRequest request) {
        Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai());

        if (dienthoaiRepository.findByTensanpham(request.getTensanpham()) != null) {
            throw new AppException(ErrorCode.DIENTHOAI);
        }

        if (thongtinphanloai == null) {
            throw new AppException(ErrorCode.THONGTINPHANLOAIDIENTHOAI);
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
        Dienthoai dienthoai =
                dienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));

        if (request.getTenphanloai() != null && !request.getTenphanloai().isEmpty()) {
            Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByTenphanloai(request.getTenphanloai());
            if (thongtinphanloai == null) {
                throw new AppException(ErrorCode.DANHMUC);
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
            List<String> ram,
            List<String> hedieuhanh,
            List<String> boNho,
            Long giaTu,
            Long giaDen,
            List<String> tinhnangdacbiet,
            List<String> tansoquet,
            List<String> kieumanhinh,
            List<String> chipset) {

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
        sql.append("JOIN webbanhang.thongsokythuat tsk ON dt.id = tsk.dienthoai_id ");
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

        if (tinhnangdacbiet != null && !tinhnangdacbiet.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < tinhnangdacbiet.size(); i++) {
                sql.append("tsk.tinhnangdacbiet LIKE ?");
                if (i < tinhnangdacbiet.size() - 1) sql.append(" OR ");
                parameters.add("%" + tinhnangdacbiet.get(i) + "%");
            }
            sql.append(") ");
        }

        if (tansoquet != null && !tansoquet.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < tansoquet.size(); i++) {
                sql.append("tsk.tansoquet LIKE ?");
                if (i < tansoquet.size() - 1) sql.append(" OR ");
                parameters.add("%" + tansoquet.get(i) + "%");
            }
            sql.append(") ");
        }

        if (kieumanhinh != null && !kieumanhinh.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < kieumanhinh.size(); i++) {
                sql.append("tsk.kieumanhinh LIKE ?");
                if (i < kieumanhinh.size() - 1) sql.append(" OR ");
                parameters.add("%" + kieumanhinh.get(i) + "%");
            }
            sql.append(") ");
        }

        if (chipset != null && !chipset.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < chipset.size(); i++) {
                sql.append("tsk.chipset LIKE ?");
                if (i < chipset.size() - 1) sql.append(" OR ");
                parameters.add("%" + chipset.get(i) + "%");
            }
            sql.append(") ");
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

    public ThongtinalldienthoaiResponse getDienthoaiDetails(Long id) {
        Dienthoai dienthoai =
                dienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));

        List<MausachienthoaiResponse> mausacResponse = dienthoai.getMausacs().stream()
                .map(mausac -> modelMapper.map(mausac, MausachienthoaiResponse.class))
                .collect(Collectors.toList());

        // Lấy đối tượng Thongsokythuat đầu tiên từ danh sách Thongsokythuat và chuyển đổi nó thành
        // ThongsokythuatdienthoaiResponse
        //  Nếu không có đối tượng nào trong danh sách Thongsokythuat, giá trị sẽ là null.
        ThongsokythuatdienthoaiResponse thongsokythuatResponse = dienthoai.getThongsokythuats().stream()
                .findFirst()
                .map(thongsokythuat -> modelMapper.map(thongsokythuat, ThongsokythuatdienthoaiResponse.class))
                .orElse(null);

        ThongtindienthoaidienthoaiResponse thongtindienthoaiResponse = dienthoai.getThongtindienthoais().stream()
                .findFirst()
                .map(thongtindienthoai -> modelMapper.map(thongtindienthoai, ThongtindienthoaidienthoaiResponse.class))
                .orElse(null);

        return ThongtinalldienthoaiResponse.builder()
                .id(dienthoai.getId())
                .tensanpham(dienthoai.getTensanpham())
                .hinhanh(dienthoai.getHinhanh())
                .hinhanhduyet(dienthoai.getHinhanhduyetAsList())
                .ram(dienthoai.getRam())
                .bonho(dienthoai.getBonho())
                .giaban(dienthoai.getGiaban())
                .mausacs(mausacResponse)
                .thongsokythuats(thongsokythuatResponse)
                .thongtindienthoai(thongtindienthoaiResponse)
                .build();
    }

    public SoSanhDienThoaiResponse soSanhDienThoai(Long id1, Long id2) {
        Dienthoai dt1 = dienthoaiRepository
                .findById(id1)
                .orElseThrow(() -> new RuntimeException("Dienthoai with id " + id1 + " not found"));
        Dienthoai dt2 = dienthoaiRepository
                .findById(id2)
                .orElseThrow(() -> new RuntimeException("Dienthoai with id " + id2 + " not found"));

        String ramComparison = compareRam(dt1.getRam(), dt2.getRam());
        String boNhoComparison = compareStorage(dt1.getBonho(), dt2.getBonho());
        String kichThuocManHinhComparison = compareScreenSize(
                dt1.getThongsokythuats().stream()
                        .findFirst()
                        .map(Thongsokythuat::getKichthuocmanhinh)
                        .orElse(null),
                dt2.getThongsokythuats().stream()
                        .findFirst()
                        .map(Thongsokythuat::getKichthuocmanhinh)
                        .orElse(null));
        String giaBanComparison = comparePrice(dt1.getGiaban(), dt2.getGiaban());

        return SoSanhDienThoaiResponse.builder()
                .id1(dt1.getId())
                .id2(dt2.getId())
                .tenSanPham1(dt1.getTensanpham())
                .tenSanPham2(dt2.getTensanpham())
                .ram1(dt1.getRam())
                .ram2(dt2.getRam())
                .boNho1(dt1.getBonho())
                .boNho2(dt2.getBonho())
                .kichThuocManHinh1(dt1.getThongsokythuats().stream()
                        .findFirst()
                        .map(Thongsokythuat::getKichthuocmanhinh)
                        .orElse(null))
                .kichThuocManHinh2(dt2.getThongsokythuats().stream()
                        .findFirst()
                        .map(Thongsokythuat::getKichthuocmanhinh)
                        .orElse(null))
                .giaBan1(dt1.getGiaban())
                .giaBan2(dt2.getGiaban())
                .ramComparison(ramComparison)
                .boNhoComparison(boNhoComparison)
                .kichThuocManHinhComparison(kichThuocManHinhComparison)
                .giaBanComparison(giaBanComparison)
                .build();
    }

    private String compareRam(String ram1, String ram2) {
        int ram1Value = extractNumericValue(ram1);
        int ram2Value = extractNumericValue(ram2);
        if (ram1Value > ram2Value) return "Phone 1 has better RAM";
        if (ram1Value < ram2Value) return "Phone 2 has better RAM";
        return "Both phones have the same RAM";
    }

    private String compareStorage(String storage1, String storage2) {
        int storage1Value = extractNumericValue(storage1);
        int storage2Value = extractNumericValue(storage2);
        if (storage1Value > storage2Value) return "Phone 1 has more storage";
        if (storage1Value < storage2Value) return "Phone 2 has more storage";
        return "Both phones have the same storage";
    }

    private String comparePin(String pin1, String pin2) {
        int pin1Value = extractNumericValue(pin1);
        int pin2Value = extractNumericValue(pin2);
        if (pin1Value > pin2Value) return "Phone 1 has a larger battery";
        if (pin1Value < pin2Value) return "Phone 2 has a larger battery";
        return "Both phones have the same battery size";
    }

    private String compareScreenSize(String size1, String size2) {
        if (size1 == null || size2 == null) return "N/A";
        double size1Value = extractNumericValue(size1);
        double size2Value = extractNumericValue(size2);
        if (size1Value > size2Value) return "Phone 1 has a bigger screen";
        if (size1Value < size2Value) return "Phone 2 has a bigger screen";
        return "Both phones have the same screen size";
    }

    private String comparePrice(String price1, String price2) {
        int price1Value = extractNumericValue(price1);
        int price2Value = extractNumericValue(price2);
        if (price1Value < price2Value) return "Phone 1 is cheaper";
        if (price1Value > price2Value) return "Phone 2 is cheaper";
        return "Both phones have the same price";
    }

    private int extractNumericValue(String value) {
        if (value == null) return 0;
        return Integer.parseInt(value.replaceAll("\\D+", ""));
    }
}
