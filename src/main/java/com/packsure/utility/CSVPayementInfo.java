package com.packsure.utility;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.csv.*;

import com.packsure.entity.PaymentInfo;

public class CSVPayementInfo {

    public static List<PaymentInfo> csvPayInfo(InputStream is) {
        try (
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            List<PaymentInfo> payInfoList = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                PaymentInfo paymentInfo = new PaymentInfo();

                String dueAmountStr = csvRecord.get("DueAmount");
                String receivedAmountStr = csvRecord.get("ReceivedAmount");

                // Safe parse: if empty or null, default to 0.0
                double dueAmount = (dueAmountStr != null && !dueAmountStr.isEmpty())
                        ? Double.parseDouble(dueAmountStr)
                        : 0.0;

                double receivedAmount = (receivedAmountStr != null && !receivedAmountStr.isEmpty())
                        ? Double.parseDouble(receivedAmountStr)
                        : 0.0;

                paymentInfo.setDueAmount(dueAmount);
                paymentInfo.setReceivedAmount(receivedAmount);

                payInfoList.add(paymentInfo);
            }

            return payInfoList;

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
        }
    }
}
