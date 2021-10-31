package com.prgm.aroundthetown.product.entity.vo;

public enum SeoulType {
    GANGNAM_YEOKSAM_SAMSUNG_NONHYUN("강남/역삼/삼성/논현"),
    SEOCHO_SINSA_BANGBAE("서초/신사/방배"),
    JAMSIL_SHINCHEON("잠실/신천(잠실새내)"),
    YEONGDEUNGPO_YEOUIDO("영등포/여의도"),
    SILLIM_SEOULDAE_SADANG_DONGJAK("신림/서울대/사당/동작"),
    CHEONHO_GILDONG_DUNCHON("천호/길동/둔촌"),
    HWAGOK_MAGCHISAN_YANGCHEON_MOKDONG("화곡/까치산/양천/목동"),
    GURO_GEUMCHEON_ORYU_SINDORIM("구로/금천/오류/신도림"),
    SINCHON_HONGDAE_HAPJEONG("신촌/홍대/합정"),
    YEONSINNAE_BULGWANG_EUNGAM("연신내/불광/응암"),
    JONGNO_DAEHAKRO_DONGMYO("종로/대학로/동묘앞역"),
    SUNGSHINYEODAE_SEONGBUK_WOLGOK("성신여대/성북/월곡"),
    ITAEWON_YONGSAN_SEOULYEOK_MYEONGDONG_HOEHYEON("이태원/용산/서울역/명동/회현"),
    DONGDAEMUN_EULJIRO_CHUNGMURO_SINDANG_YAKSU("동대문/을지로/충무로/신당/약수"),
    HOEGI_KOREADAE_CHEONGNYANGNI_SINSEOLDONG("회기/고려대/청량리/신설동"),
    JANGANDONG_DAPSIMNI("장안동/답십리"),
    KONDAE_GUNJA_GUUI("건대/군자/구의"),
    WANGSIMNI_SEONGSU_GEUMHO("왕십리/성수/금호"),
    SUYU_MIA("수유/미아"),
    SANGBONG_JUNGRANG_MYEONMOK("상봉/중랑/면목"),
    TAEREUNG_NOWON_DOBONG_CHANGDONG("태릉/노원/도봉/창동");

    private String title;

    SeoulType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
