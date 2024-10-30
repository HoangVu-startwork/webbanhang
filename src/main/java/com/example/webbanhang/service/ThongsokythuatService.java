package com.example.webbanhang.service;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.webbanhang.dto.request.ThongsokythuatRequest;
import com.example.webbanhang.dto.request.ThongsokythuatsRequest;
import com.example.webbanhang.dto.response.ThongsokythuatResponse;
import com.example.webbanhang.entity.Dienthoai;
import com.example.webbanhang.entity.Thongsokythuat;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.ThongsokythuatMapper;
import com.example.webbanhang.repository.DienthoaiRepository;
import com.example.webbanhang.repository.ThongsokythuatRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongsokythuatService {
    private final DienthoaiRepository dienthoaiRepository;
    private final ThongsokythuatRepository thongsokythuatRepository;
    private final ThongsokythuatMapper thongsokythuatMapper;

    @Transactional
    public ThongsokythuatResponse createThongsokythuat(ThongsokythuatRequest request) {
        Dienthoai dienthoai = dienthoaiRepository.findByTensanpham(request.getTensanpham());
        if (dienthoai == null) {
            throw new AppException(ErrorCode.TENDIENTHOAI);
        }

        if (thongsokythuatRepository.findByDienthoaiId(dienthoai.getId()) != null) {
            throw new AppException(ErrorCode.THONGSOKYTHUAT);
        }

        Thongsokythuat thongsokythuat = thongsokythuatMapper.toThongsokythuat(request);
        thongsokythuat.setDienthoai(dienthoai);
        Thongsokythuat savedThongsokythuat = thongsokythuatRepository.save(thongsokythuat);
        return thongsokythuatMapper.toThongsokythuatResponse(savedThongsokythuat);
    }

    public ThongsokythuatResponse findById(Long id) {
        Thongsokythuat thongsokythuat =
                thongsokythuatRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DANHMUC));
        return thongsokythuatMapper.toThongsokythuatResponse(thongsokythuat);
    }

    public ThongsokythuatResponse updateThongsokythuat(Long id, ThongsokythuatsRequest request) {
        Thongsokythuat thongsokythuat =
                thongsokythuatRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DANHMUC));

        if (request.getDienthoaiId() != null && request.getDienthoaiId() != 0) {
            Dienthoai dienthoai = dienthoaiRepository.findByid(request.getDienthoaiId());

            // Nếu tensanpham mới không tồn tại trong bảng dienthoai
            if (dienthoai == null) {
                throw new AppException(ErrorCode.TENDIENTHOAI);
            }

            // Kiểm tra nếu tensanpham mới đã tồn tại trong bảng thongtindienthoai
            Thongsokythuat existingThongsokythuat = thongsokythuatRepository.findByDienthoaiId(dienthoai.getId());
            if (existingThongsokythuat != null
                    && !existingThongsokythuat.getId().equals(thongsokythuat.getId())) {
                throw new AppException(ErrorCode.UPDATETHONGTINDIENTHOAI);
            }

            thongsokythuat.setDienthoai(dienthoai);
        }

        if (request.getKichthuocmanhinh() != null
                && !request.getKichthuocmanhinh().isEmpty()) {
            thongsokythuat.setKichthuocmanhinh(request.getKichthuocmanhinh());
        }

        if (request.getCongnghemanghinh() != null
                && !request.getCongnghemanghinh().isEmpty()) {
            thongsokythuat.setCongnghemanghinh(request.getCongnghemanghinh());
        }

        if (request.getTinhnangmanghinh() != null
                && !request.getTinhnangmanghinh().isEmpty()) {
            thongsokythuat.setTinhnangmanghinh(request.getTinhnangmanghinh());
        }

        if (request.getTansoquet() != null && !request.getTansoquet().isEmpty()) {
            thongsokythuat.setTansoquet(request.getTansoquet());
        }

        if (request.getCamerasau() != null && !request.getCamerasau().isEmpty()) {
            thongsokythuat.setCamerasau(request.getCamerasau());
        }

        if (request.getQuayvideo() != null && !request.getQuayvideo().isEmpty()) {
            thongsokythuat.setQuayvideo(request.getQuayvideo());
        }

        if (request.getTinhnagcamera() != null && !request.getTinhnagcamera().isEmpty()) {
            thongsokythuat.setTinhnagcamera(request.getTinhnagcamera());
        }

        if (request.getCameratruoc() != null && !request.getCameratruoc().isEmpty()) {
            thongsokythuat.setCameratruoc(request.getCameratruoc());
        }

        if (request.getQuayvideotruoc() != null && !request.getQuayvideotruoc().isEmpty()) {
            thongsokythuat.setQuayvideotruoc(request.getQuayvideotruoc());
        }

        if (request.getLoaicpu() != null && !request.getLoaicpu().isEmpty()) {
            thongsokythuat.setLoaicpu(request.getLoaicpu());
        }

        if (request.getDophangiai() != null && !request.getDophangiai().isEmpty()) {
            thongsokythuat.setDophangiai(request.getDophangiai());
        }

        if (request.getChipset() != null && !request.getChipset().isEmpty()) {
            thongsokythuat.setChipset(request.getChipset());
        }

        if (request.getGpu() != null && !request.getGpu().isEmpty()) {
            thongsokythuat.setGpu(request.getGpu());
        }

        if (request.getKhecamthenho() != null && !request.getKhecamthenho().isEmpty()) {
            thongsokythuat.setKhecamthenho(request.getKhecamthenho());
        }

        if (request.getPin() != null && !request.getPin().isEmpty()) {
            thongsokythuat.setPin(request.getPin());
        }

        if (request.getCongnghesac() != null && !request.getCongnghesac().isEmpty()) {
            thongsokythuat.setCongnghesac(request.getCongnghesac());
        }

        if (request.getCongsac() != null && !request.getCongsac().isEmpty()) {
            thongsokythuat.setCongsac(request.getCongsac());
        }

        if (request.getThesim() != null && !request.getThesim().isEmpty()) {
            thongsokythuat.setThesim(request.getThesim());
        }

        if (request.getHedieuhang() != null && !request.getHedieuhang().isEmpty()) {
            thongsokythuat.setHedieuhang(request.getHedieuhang());
        }

        if (request.getHongngoai() != null && !request.getHongngoai().isEmpty()) {
            thongsokythuat.setHongngoai(request.getHongngoai());
        }

        if (request.getJacktainghe() != null && !request.getJacktainghe().isEmpty()) {
            thongsokythuat.setJacktainghe(request.getJacktainghe());
        }

        if (request.getCongghenfc() != null && !request.getCongghenfc().isEmpty()) {
            thongsokythuat.setCongghenfc(request.getCongghenfc());
        }

        if (request.getHotromang() != null && !request.getHotromang().isEmpty()) {
            thongsokythuat.setHotromang(request.getHotromang());
        }

        if (request.getWifi() != null && !request.getWifi().isEmpty()) {
            thongsokythuat.setWifi(request.getWifi());
        }

        if (request.getBluetooth() != null && !request.getBluetooth().isEmpty()) {
            thongsokythuat.setBluetooth(request.getBluetooth());
        }

        if (request.getGps() != null && !request.getGps().isEmpty()) {
            thongsokythuat.setGps(request.getGps());
        }

        if (request.getKichthuoc() != null && !request.getKichthuoc().isEmpty()) {
            thongsokythuat.setKichthuoc(request.getKichthuoc());
        }

        if (request.getTrongluong() != null && !request.getTrongluong().isEmpty()) {
            thongsokythuat.setTrongluong(request.getTrongluong());
        }

        if (request.getChatlieumatlung() != null
                && !request.getChatlieumatlung().isEmpty()) {
            thongsokythuat.setChatlieumatlung(request.getChatlieumatlung());
        }

        if (request.getChatlieukhungvien() != null
                && !request.getChatlieukhungvien().isEmpty()) {
            thongsokythuat.setChatlieukhungvien(request.getChatlieukhungvien());
        }

        if (request.getChisokhangnuocbui() != null
                && !request.getChisokhangnuocbui().isEmpty()) {
            thongsokythuat.setChisokhangnuocbui(request.getChisokhangnuocbui());
        }

        if (request.getKieumanhinh() != null && !request.getKieumanhinh().isEmpty()) {
            thongsokythuat.setKieumanhinh(request.getKieumanhinh());
        }

        if (request.getCambienvantai() != null && !request.getCambienvantai().isEmpty()) {
            thongsokythuat.setCambienvantai(request.getCambienvantai());
        }

        if (request.getCacloaicambien() != null && !request.getCacloaicambien().isEmpty()) {
            thongsokythuat.setCacloaicambien(request.getCacloaicambien());
        }

        if (request.getTinhnangdacbiet() != null
                && !request.getTinhnangdacbiet().isEmpty()) {
            thongsokythuat.setTinhnangdacbiet(request.getTinhnangdacbiet());
        }

        if (request.getDacdiennoibat() != null && !request.getDacdiennoibat().isEmpty()) {
            thongsokythuat.setDacdiennoibat(request.getDacdiennoibat());
        }

        if (request.getChitiet() != null && !request.getChitiet().isEmpty()) {
            thongsokythuat.setChitiet(request.getChitiet());
        }

        Thongsokythuat updatedThongsokythuat = thongsokythuatRepository.save(thongsokythuat);
        return thongsokythuatMapper.upThongsokythuatResponse(updatedThongsokythuat);
    }
}
