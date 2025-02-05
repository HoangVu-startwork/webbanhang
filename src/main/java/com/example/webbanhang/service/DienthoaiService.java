package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.DienthoaiRequest;
import com.example.webbanhang.dto.request.DienthoaihethongRequest;
import com.example.webbanhang.dto.request.DienthoaisRequest;
import com.example.webbanhang.dto.request.SoSanhDienThoaiResponse;
import com.example.webbanhang.dto.response.*;
import com.example.webbanhang.entity.*;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.DienthoaiMapper;
import com.example.webbanhang.repository.*;

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
    KhodienthoaiRepository khodienthoaiRepository;
    JdbcTemplate jdbcTemplate;
    MausacRepository mausacRepository;

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
                .tinhtrang(request.getTinhtrang())
                .build();

        Dienthoai savedDienthoai = dienthoaiRepository.save(dienthoai);
        return dienthoaiMapper.toDienthoaiResponse(savedDienthoai);
    }

    @Transactional
    public DienthoaiResponse updateDienthoai(Long id, DienthoaisRequest request) {
        Dienthoai dienthoai =
                dienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));

        if (request.getThongtinphanloaiId() != null
                && !request.getThongtinphanloaiId()
                        .equals(dienthoai.getThongtinphanloai().getId())) {
            Thongtinphanloai thongtinphanloai = thongtinphanloaiRepository.findByid(request.getThongtinphanloaiId());
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
        if (request.getTinhtrang() != null && !request.getTinhtrang().isEmpty()) {
            dienthoai.setTinhtrang(request.getTinhtrang());
        }

        Dienthoai updatedDienthoai = dienthoaiRepository.save(dienthoai);
        return dienthoaiMapper.toDienthoaiResponse(updatedDienthoai);
    }

    public List<Map<String, Object>> getPhoneProductsWithRandomColor() {
        List<Object[]> results = dienthoaiRepository.findPhoneProductsWithRandomColor1();
        List<Map<String, Object>> phones = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> phone = new HashMap<>();
            phone.put("id", result[0]);
            phone.put("tensanpham", result[1]);
            phone.put("tenmausac", result[2]);
            phone.put("hinhanh", result[3]);
            phone.put("giaban", result[4]); // Bao gồm giá bán ở đây
            phone.put("mausac_id", result[12]);

            // Thêm các trường khuyến mãi
            phone.put("phantramkhuyenmai", result[5]);
            phone.put("noidungkhuyenmai", result[6]);
            phone.put("ngaybatdau", result[7]);
            phone.put("ngayketkhuc", result[8]);

            // Thêm các trường thông tin điện thoại
            phone.put("tinhtrangmay", result[9]);
            phone.put("thietbidikem", result[10]);
            phone.put("baohanh", result[11]);

            phones.add(phone);
        }

        return phones;
    }

    public List<DienthoaihethongResponse> getAllPhoneWithColors() {
        List<Object[]> results = dienthoaiRepository.findPhoneProductsWithAllColors();
        List<DienthoaihethongResponse> responses = new ArrayList<>();

        for (Object[] row : results) {
            Long id = ((Number) row[0]).longValue();
            String tensanpham = (String) row[1];
            String hinhanh = (String) row[2];
            String hinhanhduyet = (String) row[3];
            String ram = (String) row[4];
            String bonho = (String) row[5];
            String giaban = (String) row[6];
            String tinhtrang = (String) row[7];

            // Split concatenated fields into lists
            String tenMauSacStr = row[8] != null ? (String) row[8] : "";
            String idMauSacStr = row[9] != null ? (String) row[9] : "";
            String hinhAnhStr = row[10] != null ? (String) row[10] : "";
            String giaBanStr = row[11] != null ? (String) row[11] : "";

            // Nếu row[7] (hoặc row[8], row[9], row[10]) không phải null, nó sẽ gán giá trị của row[7] vào biến
            // tenMauSacStr. Nếu row[7] là null, nó sẽ gán chuỗi rỗng ("").
            List<String> tenmausacs =
                    tenMauSacStr.isEmpty() ? new ArrayList<>() : Arrays.asList(tenMauSacStr.split(","));
            List<String> idmausacs = idMauSacStr.isEmpty() ? new ArrayList<>() : Arrays.asList(idMauSacStr.split(","));
            List<String> hinhanhs = hinhAnhStr.isEmpty() ? new ArrayList<>() : Arrays.asList(hinhAnhStr.split(","));
            List<String> giabans = giaBanStr.isEmpty() ? new ArrayList<>() : Arrays.asList(giaBanStr.split(","));
            // isEmpty kiểm tra xem chuỗi có rỗng hay không
            // Arrays.asList() chuyển mảng thành danh sách (List)
            // split(",") tách chuỗi thành các phần tử riêng biệt

            List<MausachienthoaiResponse> mausacResponses = new ArrayList<>();
            for (int i = 0; i < tenmausacs.size(); i++) {
                MausachienthoaiResponse mausacResponse = MausachienthoaiResponse.builder()
                        .id(Long.parseLong(idmausacs.get(i)))
                        .tenmausac(tenmausacs.get(i))
                        .giaban(giabans.get(i))
                        .hinhanh(hinhanhs.get(i))
                        .build();
                mausacResponses.add(mausacResponse);
            }

            DienthoaihethongResponse dienthoaiResponse = DienthoaihethongResponse.builder()
                    .id(id)
                    .tensanpham(tensanpham)
                    .hinhanh(hinhanh)
                    .hinhanhduyet(hinhanhduyet)
                    .ram(ram)
                    .bonho(bonho)
                    .giaban(giaban)
                    .mausacs(mausacResponses)
                    .tinhtrang(tinhtrang)
                    .build();

            responses.add(dienthoaiResponse);
        }

        return responses;
    }

    public List<Map<String, Object>> getPhoneProductsWithFilters(
            List<String> ram,
            List<String> hedieuhanh,
            List<String> boNho,
            Long giaTu,
            Long giaDen,
            List<String> tinhnangdacbiet,
            List<String> kichthuocmanhinh,
            List<String> tinhnagcamera,
            List<String> tansoquet,
            List<String> kieumanhinh,
            List<String> thietbidikem,
            List<String> chipset) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT dt.id, dt.tensanpham, ms.tenmausac, ms.hinhanh, ms.giaban, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.phantramkhuyenmai ELSE NULL END AS phantramkhuyenmai, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.noidungkhuyenmai ELSE NULL END AS noidungkhuyenmai, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.ngaybatdau ELSE NULL END AS ngaybatdau, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.ngayketkhuc ELSE NULL END AS ngayketkhuc, ");
        sql.append("tt.tinhtrangmay, tt.thietbidikem, tt.baohanh, ");
        sql.append("ms.id AS mausac_id, ");
        sql.append("tt.id AS thongtindienthoai_id, ");
        sql.append("km.id AS khuyenmai_id, ");
        sql.append("COALESCE(kd.soluong, 0) AS soluong "); // Thêm trường số lượng vào đây
        sql.append("FROM dienthoai dt ");
        sql.append("JOIN ( ");
        sql.append("    SELECT m.dienthoai_id, m.tenmausac, m.hinhanh, m.giaban, m.id ");
        sql.append("    FROM mausac m ");
        sql.append("    JOIN ( ");
        sql.append("        SELECT dienthoai_id, MIN(id) AS id ");
        sql.append("        FROM mausac ");
        sql.append("        GROUP BY dienthoai_id ");
        sql.append("    ) sub ON m.dienthoai_id = sub.dienthoai_id AND m.id = sub.id ");
        sql.append(") ms ON dt.id = ms.dienthoai_id ");
        sql.append("LEFT JOIN thongtinphanloai ttpl ON dt.thongtinphanloai_id = ttpl.id ");
        sql.append("LEFT JOIN loaisanpham lsp ON ttpl.loaisanpham_id = lsp.id ");
        sql.append("LEFT JOIN danhmuc dm ON lsp.danhmuc_id = dm.id ");
        sql.append("LEFT JOIN hedieuhanh hd ON dm.hedieuhanh_id = hd.id ");
        sql.append("LEFT JOIN thongsokythuat tsk ON dt.id = tsk.dienthoai_id ");
        sql.append("LEFT JOIN thongtindienthoai tt ON dt.id = tt.dienthoai_id ");
        sql.append("LEFT JOIN khuyenmai km ON dt.id = km.dienthoai_id ");
        sql.append("AND CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc ");
        sql.append("LEFT JOIN khodienthoai kd ON dt.id = kd.dienthoai_id AND ms.id = kd.mausac_id ");
        sql.append("WHERE dt.tinhtrang = 'Mở' ");
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

        if (kichthuocmanhinh != null && !kichthuocmanhinh.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < kichthuocmanhinh.size(); i++) {
                String size = kichthuocmanhinh.get(i);
                try {
                    double sizeValue = Double.parseDouble(size);
                    if (sizeValue < 6) {
                        sql.append("tsk.kichthuocmanhinh < 6");
                    } else {
                        sql.append("tsk.kichthuocmanhinh >= 6");
                    }
                } catch (NumberFormatException e) {
                    sql.append("tsk.kichthuocmanhinh LIKE ?");
                    parameters.add("%" + size + "%");
                }
                if (i < kichthuocmanhinh.size() - 1) sql.append(" OR ");
            }
            sql.append(") ");
        }

        if (tinhnagcamera != null && !tinhnagcamera.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < tinhnagcamera.size(); i++) {
                sql.append("tsk.tinhnagcamera LIKE ?");
                if (i < tinhnagcamera.size() - 1) sql.append(" OR ");
                parameters.add("%" + tinhnagcamera.get(i) + "%");
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

        if (thietbidikem != null && !thietbidikem.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < thietbidikem.size(); i++) {
                sql.append("tt.thietbidikem LIKE ?");
                if (i < thietbidikem.size() - 1) sql.append(" OR ");
                parameters.add("%" + thietbidikem.get(i) + "%");
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

        // Note: Ensure that parameters are added in the correct order and match the placeholders in the SQL query.

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

    //    public ThongtinalldienthoaiResponse getDienthoaiDetails(Long id, Long mausacId) {
    //        Dienthoai dienthoai =
    //                dienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));
    //
    //        List<MausachienthoaiResponse> mausacResponse = dienthoai.getMausacs().stream()
    //                .map(mausac -> modelMapper.map(mausac, MausachienthoaiResponse.class))
    //                .toList();
    //
    //        List<MausachienthoaiResponse> mausacResponset = dienthoai.getMausacs().stream()
    //                .filter(mausac -> mausac.getId().equals(mausacId)) // Lọc theo mausacId
    //                .map(mausac -> modelMapper.map(mausac, MausachienthoaiResponse.class))
    //                .toList();
    //
    //        // Lấy đối tượng Thongsokythuat đầu tiên từ danh sách Thongsokythuat và chuyển đổi nó thành
    //        // ThongsokythuatdienthoaiResponse
    //        //  Nếu không có đối tượng nào trong danh sách Thongsokythuat, giá trị sẽ là null.
    //        ThongsokythuatdienthoaiResponse thongsokythuatResponse = dienthoai.getThongsokythuats().stream()
    //                .findFirst()
    //                .map(thongsokythuat -> modelMapper.map(thongsokythuat, ThongsokythuatdienthoaiResponse.class))
    //                .orElse(null);
    //
    //        ThongtindienthoaidienthoaiResponse thongtindienthoaiResponse = dienthoai.getThongtindienthoais().stream()
    //                .findFirst()
    //                .map(thongtindienthoai -> modelMapper.map(thongtindienthoai,
    // ThongtindienthoaidienthoaiResponse.class))
    //                .orElse(null);
    //
    //        Khodienthoai khodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(id, mausacId);
    //        String soluongTonkho = (khodienthoai != null) ? khodienthoai.getSoluong() : "0";
    //
    //        Thongtinphanloai thongtinphanloai = dienthoaiRepository.findThongtinphanloaiByDienthoaiId(id);
    //        ThongtinphanloaidienthoaiResponse thongtinphanloaiResponse =
    //                modelMapper.map(thongtinphanloai, ThongtinphanloaidienthoaiResponse.class);
    //
    //        return ThongtinalldienthoaiResponse.builder()
    //                .id(dienthoai.getId())
    //                .tensanpham(dienthoai.getTensanpham())
    //                .hinhanh(dienthoai.getHinhanh())
    //                .hinhanhduyet(dienthoai.getHinhanhduyetAsList())
    //                .ram(dienthoai.getRam())
    //                .bonho(dienthoai.getBonho())
    //                .giaban(dienthoai.getGiaban())
    //                .mausacs(mausacResponse)
    //                .mausactong(mausacResponset)
    //                .thongsokythuats(thongsokythuatResponse)
    //                .thongtindienthoai(thongtindienthoaiResponse)
    //                .soluongTonKho(soluongTonkho)
    //                .thongtinphanloai(thongtinphanloaiResponse)
    //                .build();
    //    }
    public ThongtinalldienthoaiResponse getDienthoaiDetails(Long id, Long mausacId) {
        Dienthoai dienthoai =
                dienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        List<MausachienthoaiResponse> mausacResponse = dienthoai.getMausacs().stream()
                .map(mausac -> modelMapper.map(mausac, MausachienthoaiResponse.class))
                .toList();

        List<MausachienthoaiResponse> mausacResponset = dienthoai.getMausacs().stream()
                .filter(mausac -> mausac.getId().equals(mausacId)) // Lọc theo mausacId
                .map(mausac -> modelMapper.map(mausac, MausachienthoaiResponse.class))
                .toList();

        ThongsokythuatdienthoaiResponse thongsokythuatResponse = dienthoai.getThongsokythuats().stream()
                .findFirst()
                .map(thongsokythuat -> modelMapper.map(thongsokythuat, ThongsokythuatdienthoaiResponse.class))
                .orElse(null);

        ThongtindienthoaidienthoaiResponse thongtindienthoaiResponse = dienthoai.getThongtindienthoais().stream()
                .findFirst()
                .map(thongtindienthoai -> modelMapper.map(thongtindienthoai, ThongtindienthoaidienthoaiResponse.class))
                .orElse(null);

        Khodienthoai khodienthoai = khodienthoaiRepository.findByDienthoaiIdAndMausacId(id, mausacId);
        String soluongTonkho = (khodienthoai != null) ? khodienthoai.getSoluong() : "0";

        Thongtinphanloai thongtinphanloai = dienthoaiRepository.findThongtinphanloaiByDienthoaiId(id);
        ThongtinphanloaidienthoaiResponse thongtinphanloaiResponse =
                modelMapper.map(thongtinphanloai, ThongtinphanloaidienthoaiResponse.class);

        // Find the first valid promotion
        KhuyenmaiResponse khuyenmaiResponse = dienthoai.getKhuyenmais().stream()
                .filter(khuyenmai -> {
                    LocalDateTime ngayBatDau = LocalDateTime.parse(khuyenmai.getNgaybatdau());
                    LocalDateTime ngayKetThuc = LocalDateTime.parse(khuyenmai.getNgayketkhuc());
                    return (ngayBatDau.isBefore(currentDateTime) || ngayBatDau.isEqual(currentDateTime))
                            && (ngayKetThuc.isAfter(currentDateTime) || ngayKetThuc.isEqual(currentDateTime));
                })
                .findFirst() // Return the first matching promotion
                .map(khuyenmai -> modelMapper.map(khuyenmai, KhuyenmaiResponse.class))
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
                .mausactong(mausacResponset)
                .thongsokythuats(thongsokythuatResponse)
                .thongtindienthoai(thongtindienthoaiResponse)
                .soluongTonKho(soluongTonkho)
                .thongtinphanloai(thongtinphanloaiResponse)
                .khuyenmais(khuyenmaiResponse) // Add the single promotion to the response
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

    public List<DienthoaihethongRequest> getDienthoaiByHedieuhanhId(Long hedieuhanhId) {
        List<Dienthoai> dienthoais = dienthoaiRepository.findByHedieuhanhId(hedieuhanhId);
        return dienthoais.stream().map(this::toDienthoai).collect(Collectors.toList());
    }

    public List<DienthoaihethongRequest> getLoaisanphamId(Long loaisanphamId) {
        List<Dienthoai> dienthoais = dienthoaiRepository.findByLoaisanphamId(loaisanphamId);
        return dienthoais.stream().map(this::toDienthoai).collect(Collectors.toList());
    }

    public List<DienthoaihethongRequest> getDanhmuc(Long danhmucId) {
        List<Dienthoai> dienthoais = dienthoaiRepository.findByDanhmucId(danhmucId);
        return dienthoais.stream().map(this::toDienthoai).collect(Collectors.toList());
    }

    public List<DienthoaihethongResponse> getThongtinphanloai(Long thongtinphanloaiId) {
        List<Dienthoai> dienthoais = dienthoaiRepository.findByThongtinphanloaiId(thongtinphanloaiId);
        return dienthoais.stream().map(this::tothongtinphanloaiDienthoai).collect(Collectors.toList());
    }

    private DienthoaihethongResponse tothongtinphanloaiDienthoai(Dienthoai dienthoai) {
        List<MausachienthoaiResponse> mausacResponse = dienthoai.getMausacs().stream()
                .map(mausac -> modelMapper.map(mausac, MausachienthoaiResponse.class))
                .collect(Collectors.toList());

        return DienthoaihethongResponse.builder()
                .id(dienthoai.getId())
                .tensanpham(dienthoai.getTensanpham())
                .hinhanh(dienthoai.getHinhanh())
                .hinhanhduyet(dienthoai.getHinhanhduyet())
                .ram(dienthoai.getRam())
                .bonho(dienthoai.getBonho())
                .giaban(dienthoai.getGiaban())
                .tenphanloai(dienthoai.getThongtinphanloai().getTenphanloai())
                .mausacs(mausacResponse)
                .build();
    }

    private DienthoaihethongRequest toDienthoai(Dienthoai dienthoai) {
        return DienthoaihethongRequest.builder()
                .id(dienthoai.getId())
                .tensanpham(dienthoai.getTensanpham())
                .hinhanh(dienthoai.getHinhanh())
                .hinhanhduyet(dienthoai.getHinhanhduyet())
                .ram(dienthoai.getRam())
                .bonho(dienthoai.getBonho())
                .giaban(dienthoai.getGiaban())
                .tenphanloai(dienthoai.getThongtinphanloai().getTenphanloai())
                .build();
    }

    public ThongtinphanloaidienthoaiResponse getThongtinphanloaiByDienthoaiId(Long dienthoaiId) {
        Thongtinphanloai thongtinphanloai = dienthoaiRepository.findThongtinphanloaiByDienthoaiId(dienthoaiId);
        if (thongtinphanloai != null) {
            return ThongtinphanloaidienthoaiResponse.builder()
                    .id(thongtinphanloai.getId())
                    .tenphanloai(thongtinphanloai.getTenphanloai())
                    .build();
        }
        return null;
    }

    //    get dữ liệu điện thoại admin
    public List<Map<String, Object>> getPhoneProductsWithFilters1(
            // List<Map<String, Object>> là cấu trúc dữ liệu được sử dụng để lưu trữ một danh sách các bản ghi, trong đó
            // mỗi bản ghi được đại diện bởi một Map. Cụ thể, đây là một danh sách (List) mà mỗi phần tử của nó là một
            // Map với các khóa là kiểu String và giá trị là kiểu Object
            List<String> ram,
            List<String> hedieuhanh,
            List<String> boNho,
            Long giaTu,
            Long giaDen,
            List<String> tinhnangdacbiet,
            List<String> kichthuocmanhinh,
            List<String> tinhnagcamera,
            List<String> tansoquet,
            List<String> kieumanhinh,
            List<String> thietbidikem,
            List<String> tinhtrang,
            String tensanpham,
            List<String> chipset) {

        StringBuilder sql = new StringBuilder();
        sql.append(
                "SELECT dt.id, dt.tensanpham, dt.tinhtrang, dt.hinhanh, dt.hinhanhduyet, dt.giaban, dt.ram, dt.bonho, ");
        sql.append(
                "GROUP_CONCAT(ms.id, ',', ms.tenmausac, ',', ms.hinhanh, ',', ms.giaban, ',', COALESCE(kd.soluong, 0) ORDER BY ms.id SEPARATOR ';') AS mausac_list, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.phantramkhuyenmai ELSE NULL END AS phantramkhuyenmai, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.noidungkhuyenmai ELSE NULL END AS noidungkhuyenmai, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.ngaybatdau ELSE NULL END AS ngaybatdau, ");
        sql.append(
                "CASE WHEN CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc THEN km.ngayketkhuc ELSE NULL END AS ngayketkhuc, ");
        sql.append("tt.id AS thongtindienthoai_id, ");
        sql.append("tsk.id AS thongsokythuat_id, ");
        sql.append("km.id AS khuyenmai_id ");
        sql.append("FROM dienthoai dt ");
        sql.append("LEFT JOIN mausac ms ON dt.id = ms.dienthoai_id ");
        sql.append("LEFT JOIN thongtinphanloai ttpl ON dt.thongtinphanloai_id = ttpl.id ");
        sql.append("LEFT JOIN loaisanpham lsp ON ttpl.loaisanpham_id = lsp.id ");
        sql.append("LEFT JOIN danhmuc dm ON lsp.danhmuc_id = dm.id ");
        sql.append("LEFT JOIN hedieuhanh hd ON dm.hedieuhanh_id = hd.id ");
        sql.append("LEFT JOIN thongsokythuat tsk ON dt.id = tsk.dienthoai_id ");
        sql.append("LEFT JOIN thongtindienthoai tt ON dt.id = tt.dienthoai_id ");
        sql.append("LEFT JOIN khuyenmai km ON dt.id = km.dienthoai_id ");
        sql.append("AND CURRENT_TIMESTAMP BETWEEN km.ngaybatdau AND km.ngayketkhuc ");
        sql.append("LEFT JOIN khodienthoai kd ON dt.id = kd.dienthoai_id AND ms.id = kd.mausac_id ");
        sql.append("WHERE 1=1 ");
        List<Object> parameters = new ArrayList<>();
        // Thêm các điều kiện lọc cho RAM
        if (ram != null && !ram.isEmpty()) {
            sql.append("AND dt.ram IN (");
            for (int i = 0; i < ram.size(); i++) {
                sql.append("?");
                if (i < ram.size() - 1) sql.append(", ");
                parameters.add(ram.get(i));
            }
            sql.append(") ");
        }

        // Thêm điều kiện cho hệ điều hành
        if (hedieuhanh != null && !hedieuhanh.isEmpty()) {
            sql.append("AND hd.tenhedieuhanh IN (");
            for (int i = 0; i < hedieuhanh.size(); i++) {
                sql.append("?");
                if (i < hedieuhanh.size() - 1) sql.append(", ");
                parameters.add(hedieuhanh.get(i));
            }
            sql.append(") ");
        }

        // Thêm điều kiện cho bộ nhớ
        if (boNho != null && !boNho.isEmpty()) {
            sql.append("AND dt.bonho IN (");
            for (int i = 0; i < boNho.size(); i++) {
                sql.append("?");
                if (i < boNho.size() - 1) sql.append(", ");
                parameters.add(boNho.get(i));
            }
            sql.append(") ");
        }

        // Thêm điều kiện cho giá
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

        if (kichthuocmanhinh != null && !kichthuocmanhinh.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < kichthuocmanhinh.size(); i++) {
                String size = kichthuocmanhinh.get(i);
                try {
                    double sizeValue = Double.parseDouble(size);
                    if (sizeValue < 6) {
                        sql.append("tsk.kichthuocmanhinh < 6");
                    } else {
                        sql.append("tsk.kichthuocmanhinh >= 6");
                    }
                } catch (NumberFormatException e) {
                    sql.append("tsk.kichthuocmanhinh LIKE ?");
                    parameters.add("%" + size + "%");
                }
                if (i < kichthuocmanhinh.size() - 1) sql.append(" OR ");
            }
            sql.append(") ");
        }

        if (tinhnagcamera != null && !tinhnagcamera.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < tinhnagcamera.size(); i++) {
                sql.append("tsk.tinhnagcamera LIKE ?");
                if (i < tinhnagcamera.size() - 1) sql.append(" OR ");
                parameters.add("%" + tinhnagcamera.get(i) + "%");
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

        if (thietbidikem != null && !thietbidikem.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < thietbidikem.size(); i++) {
                sql.append("tt.thietbidikem LIKE ?");
                if (i < thietbidikem.size() - 1) sql.append(" OR ");
                parameters.add("%" + thietbidikem.get(i) + "%");
            }
            sql.append(") ");
        }

        if (tinhtrang != null && !tinhtrang.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < tinhtrang.size(); i++) {
                sql.append("dt.tinhtrang LIKE ?");
                if (i < tinhtrang.size() - 1) sql.append(" OR ");
                parameters.add("%" + tinhtrang.get(i) + "%");
            }
            sql.append(") ");
        }

        if (tensanpham != null && !tensanpham.isEmpty()) {
            sql.append("AND dt.tensanpham COLLATE utf8mb4_general_ci LIKE ? ");
            parameters.add("%" + tensanpham + "%");
        }

        // COLLATE utf8mb4_general_ci: Loại bỏ sự phân biệt dấu và chữ hoa/chữ thường khi so sánh chuỗi trong MySQL. Đây
        // là bộ sắp xếp ký tự phổ biến dùng cho tiếng Việt.

        if (chipset != null && !chipset.isEmpty()) {
            sql.append("AND (");
            for (int i = 0; i < chipset.size(); i++) {
                sql.append("tsk.chipset LIKE ?");
                if (i < chipset.size() - 1) sql.append(" OR ");
                parameters.add("%" + chipset.get(i) + "%");
            }
            sql.append(") ");
        }

        sql.append("GROUP BY dt.id, tt.id, tsk.id, km.id ");

        // Thực hiện truy vấn
        List<Map<String, Object>> results = jdbcTemplate.query(sql.toString(), parameters.toArray(), (rs, rowNum) -> {
            // sql.toString là phương thức của StringBuilder dùng để chuyển nội dung của đối tượng sql (câu truy vấn đã
            // được xây dựng) thành một chuỗi hoàn chỉnh (kiểu String). Khi đó, chuỗi này sẽ là truy vấn SQL cuối cùng
            // được thực thi bởi jdbcTemplate.

            // parameters.toArray()
            // parameters là một List<Object> (danh sách các đối tượng) chứa các giá trị cần truyền vào câu truy vấn
            // SQL. Các giá trị này được sử dụng để thay thế các dấu ? trong câu truy vấn SQL.
            // parameters.toArray() là phương thức chuyển đổi danh sách parameters thành một mảng Object[] (mảng các đối
            // tượng), vì phương thức query() của jdbcTemplate yêu cầu một mảng để sử dụng các tham số.

            // (rs, rowNum)
            // Đây là tham số của lambda expression, được sử dụng như một phần của RowMapper trong jdbcTemplate. Cụ thể:
            // rs: Là viết tắt của ResultSet, đại diện cho kết quả của truy vấn SQL. ResultSet chứa dữ liệu từ các cột
            // và hàng của bảng trong cơ sở dữ liệu.
            // Bạn có thể truy xuất dữ liệu từ ResultSet bằng các phương thức như rs.getString(), rs.getLong(), v.v. để
            // lấy dữ liệu từ các cột trong kết quả.
            // rowNum: Là số thứ tự của dòng hiện tại trong ResultSet. Mỗi lần phương thức ánh xạ (mapping) xử lý một
            // hàng của ResultSet, rowNum sẽ tăng lên một giá trị.
            Map<String, Object> row = new LinkedHashMap<>(); // Dùng LinkedHashMap để duy trì thứ tự
            row.put("id", rs.getLong("id"));
            row.put("tensanpham", rs.getString("tensanpham"));
            row.put("tinhtrang", rs.getString("tinhtrang"));
            row.put("hinhanh", rs.getString("hinhanh"));
            row.put("hinhanhduyet", rs.getString("hinhanhduyet"));
            row.put("giaban", rs.getString("giaban"));
            row.put("ram", rs.getString("ram"));
            row.put("bonho", rs.getString("bonho"));
            row.put("phantramkhuyenmai", rs.getObject("phantramkhuyenmai"));
            row.put("noidungkhuyenmai", rs.getObject("noidungkhuyenmai"));
            row.put("ngaybatdau", rs.getObject("ngaybatdau"));
            row.put("ngayketkhuc", rs.getObject("ngayketkhuc"));
            row.put("thongtindienthoai_id", rs.getLong("thongtindienthoai_id"));
            row.put("thongsokythuat_id", rs.getLong("thongsokythuat_id"));
            row.put("khuyenmai_id", rs.getLong("khuyenmai_id"));
            // Xử lý danh sách màu sắc
            //            String mausacList = rs.getString("mausac_list");
            //            if (mausacList != null && !mausacList.isEmpty()) {
            //                List<Map<String, Object>> mausacs = new ArrayList<>();
            //                String[] mausacItems = mausacList.split(";");
            //                for (String mausacItem : mausacItems) {
            //                    String[] mausacDetails = mausacItem.split(",");
            //                    Map<String, Object> mausac = new HashMap<>();
            //                    mausac.put("id", Long.parseLong(mausacDetails[0]));
            //                    mausac.put("tenmausac", mausacDetails[1]);
            //                    mausac.put("hinhanh", mausacDetails[2]);
            //                    mausac.put("giaban", Double.parseDouble(mausacDetails[3]));
            //                    mausac.put("soluong", Integer.parseInt(mausacDetails[4])); // Số lượng theo màu sắc
            //                    mausacs.add(mausac);
            //                }
            //                row.put("mausacs", mausacs);
            //            }
            String mausacList = rs.getString("mausac_list");
            if (mausacList != null && !mausacList.isEmpty()) {
                String[] mausacArray = mausacList.split(";");
                List<Map<String, Object>> mausacDetails = new ArrayList<>();
                for (String mausac : mausacArray) {
                    String[] mausacAttributes = mausac.split(",");
                    Map<String, Object> mausacMap = new LinkedHashMap<>();
                    mausacMap.put("id", mausacAttributes[0]);
                    mausacMap.put("tenmausac", mausacAttributes[1]);
                    mausacMap.put("hinhanh", mausacAttributes[2]);
                    mausacMap.put("giaban", mausacAttributes[3]);
                    mausacMap.put("soluong", mausacAttributes[4]);
                    mausacDetails.add(mausacMap);
                }
                row.put("mausac_list", mausacDetails);
            }

            return row;
        });

        return results;
    }

    public DienthoaiResponse getDienthoaiById(Long id) {
        Dienthoai dienthoai =
                dienthoaiRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TENDIENTHOAI));
        return dienthoaiMapper.toDienthoaiResponse(dienthoai);
    }

    public List<DienthoaiResponse> findAlldienthoai() {
        List<Dienthoai> dienthoais = dienthoaiRepository.findAll();
        return dienthoais.stream().map(dienthoaiMapper::toDienthoaiResponse).toList();
    }

    public String checkDienthoaiMausac(Long dienthoaiId, Long mausacId) {
        boolean exists = mausacRepository.existsByDienthoai_IdAndId(dienthoaiId, mausacId);
        return exists ? "Có" : "Không";
    }

    public void deleteDienthoai(Long id) {
        dienthoaiRepository.deleteById(id);
    }
}
