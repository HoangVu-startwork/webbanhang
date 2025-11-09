package com.example.webbanhang.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.webbanhang.dto.request.ApiResponse;
import com.example.webbanhang.dto.request.HoadonRequest;
import com.example.webbanhang.dto.response.DailySummaryResponse;
import com.example.webbanhang.dto.response.HoadonResponse;
import com.example.webbanhang.dto.response.HoadonSummaryResponse;
import com.example.webbanhang.dto.response.MonthlySummaryResponse;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.HoadonMapper;
import com.example.webbanhang.service.HoadonService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hoadon")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HoadonController {
    private final HoadonService hoadonService;
    HoadonMapper hoadonMapper;

    @PostMapping
    public ApiResponse<HoadonResponse> createHoadon(@RequestBody HoadonRequest request) {
        HoadonResponse response = hoadonService.createHoadon(request);
        return ApiResponse.<HoadonResponse>builder().result(response).build();
    }

    @PostMapping("/hoadon")
    public ApiResponse<HoadonResponse> createHoadon1(@RequestBody HoadonRequest request) {
        HoadonResponse response = hoadonService.createHoadon1(request);
        return ApiResponse.<HoadonResponse>builder().result(response).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<HoadonResponse> getHoadonById(@PathVariable Long id) {
        return ApiResponse.<HoadonResponse>builder()
                .result(hoadonService.getHoadonById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<HoadonResponse>> getAllHoadon() {
        return ApiResponse.<List<HoadonResponse>>builder()
                .result(hoadonService.getAllHoadon())
                .build();
    }

    // liệt kê tiền và số hoá đơn trong cadl theo ngày
    @GetMapping("/summary/all")
    public ApiResponse<List<DailySummaryResponse>> getAllDailySummaries() {
        List<DailySummaryResponse> summaries = hoadonService.getAllDailySummaries();
        return ApiResponse.<List<DailySummaryResponse>>builder()
                .result(summaries)
                .build();
    }

    @GetMapping("user/{userId}")
    ApiResponse<List<HoadonResponse>> getHoadonByUserId(@PathVariable String userId) {
        return ApiResponse.<List<HoadonResponse>>builder()
                .result(hoadonService.getHoadonByUserId(userId))
                .build();
    }

    // Liệt kê tiền và số hoá đơn trong cadl theo tháng và lọc hoá đơn theo tháng năm
    @GetMapping("/statistics/month")
    public ApiResponse<MonthlySummaryResponse> getMonthlyStatistics(
            @RequestParam("year") int year, @RequestParam("month") int month) {
        if (month < 1 || month > 12) {
            throw new AppException(ErrorCode.INVALID_MONTH);
        }

        return ApiResponse.<MonthlySummaryResponse>builder()
                .result(hoadonService.getMoonthlyStatistics(year, month))
                .build();
    }

    @GetMapping("/statistics/overall")
    public ApiResponse<HoadonSummaryResponse> getOverallStatistics() {
        return ApiResponse.<HoadonSummaryResponse>builder()
                .result(hoadonService.getHoadonSummary())
                .build();
    }

    @GetMapping("/statistics/user/{userId}")
    public ApiResponse<HoadonSummaryResponse> getHoadonSummaryByUserId(@PathVariable String userId) {
        return ApiResponse.<HoadonSummaryResponse>builder()
                .result(hoadonService.getUserSummary(userId))
                .build();
    }
}
