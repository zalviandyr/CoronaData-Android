package com.zukron.coronadataapp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/12/2020
 */
public class Province extends StatusData {
    private static Map<String, Integer> allProvinceId = new HashMap<>();

    public static ArrayList<String> allProvince() {
        ArrayList<String> provinces = new ArrayList<>();

        provinces.add("Aceh");
        provinces.add("Bali");
        provinces.add("Banten");
        provinces.add("Bengkulu");
        provinces.add("Daerah Istimewa Yogyakarta");
        provinces.add("DKI Jakarta");
        provinces.add("Gorontalo");
        provinces.add("Jambi");
        provinces.add("Jawa Barat");
        provinces.add("Jawa Tengah");
        provinces.add("Jawa Timur");
        provinces.add("Kalimantan Barat");
        provinces.add("Kalimantan Selatan");
        provinces.add("Kalimantan Tengah");
        provinces.add("Kalimantan Timur");
        provinces.add("Kalimantan Utara");
        provinces.add("Kepulauan Bangka Belitung");
        provinces.add("Kepulauan Riau");
        provinces.add("Lampung");
        provinces.add("Maluku");
        provinces.add("Maluku Utara");
        provinces.add("Nusa Tenggara Barat");
        provinces.add("Nusa Tenggara Timur");
        provinces.add("Papua");
        provinces.add("Papua Barat");
        provinces.add("Riau");
        provinces.add("Sulawesi Barat");
        provinces.add("Sulawesi Selatan");
        provinces.add("Sulawesi Tengah");
        provinces.add("Sulawesi Tenggara");
        provinces.add("Sulawesi Utara");
        provinces.add("Sumatera Barat");
        provinces.add("Sumatera Selatan");
        provinces.add("Sumatera Utara");

        return provinces;
    }

    private static void setAllProvinceId() {
        allProvinceId.put("Jawa_Timur", 35);
        allProvinceId.put("DKI_Jakarta", 31);
        allProvinceId.put("Sulawesi_Selatan", 73);
        allProvinceId.put("Jawa_Tengah", 33);
        allProvinceId.put("Jawa_Barat", 32);
        allProvinceId.put("Kalimantan_Selatan", 63);
        allProvinceId.put("Sumatera_Selatan", 16);
        allProvinceId.put("Papua", 94);
        allProvinceId.put("Bali", 51);
        allProvinceId.put("Sumatera_Utara", 12);
        allProvinceId.put("Banten", 36);
        allProvinceId.put("Nusa_Tenggara_Barat", 52);
        allProvinceId.put("Sulawesi_Utara", 71);
        allProvinceId.put("Kalimantan_Tengah", 62);
        allProvinceId.put("Maluku_Utara", 82);
        allProvinceId.put("Maluku", 81);
        allProvinceId.put("Sumatera_Barat", 13);
        allProvinceId.put("Kalimantan_Timur", 64);
        allProvinceId.put("Sulawesi_Tenggara", 74);
        allProvinceId.put("Daerah_Istimewa_Yogyakarta", 34);
        allProvinceId.put("Kalimantan_Barat", 61);
        allProvinceId.put("Kepulauan_Riau", 21);
        allProvinceId.put("Gorontalo", 75);
        allProvinceId.put("Papua_Barat", 91);
        allProvinceId.put("Riau", 14);
        allProvinceId.put("Kalimantan_Utara", 65);
        allProvinceId.put("Lampung", 18);
        allProvinceId.put("Sulawesi_Tengah", 72);
        allProvinceId.put("Kepulauan_Bangka_Belitung", 19);
        allProvinceId.put("Bengkulu", 17);
        allProvinceId.put("Sulawesi_Barat", 76);
        allProvinceId.put("Jambi", 15);
        allProvinceId.put("Nusa_Tenggara_Timur", 53);
        allProvinceId.put("Aceh", 11);
    }

    public static Integer getProvinceId(String province) {
        setAllProvinceId();
        return allProvinceId.get(province);
    }
}
