package edu.illinois.awikum.mp7;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import edu.illinois.awikum.library.MyClass;

public class stocks extends Fragment{
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "MP7:Main";
    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    //dia,ivv,oneq,goog,aapl,msft,eem,efa,spy
    private JsonObject dia = new JsonObject();
    private JsonObject ivv = new JsonObject();
    private JsonObject oneq = new JsonObject();
    private JsonObject goog = new JsonObject();
    private JsonObject aapl = new JsonObject();
    private JsonObject msft = new JsonObject();
    private JsonObject eem = new JsonObject();
    private JsonObject efa = new JsonObject();
    private JsonObject spy = new JsonObject();

    JsonObject[] createCompanies = {dia, ivv, oneq, goog, aapl, msft, eem, efa, spy};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.stocks, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        startCreateStockAPICall();

        String[] company_list={"AGILENT TECHNOLOGIES INC (A) ","ALCOA CORP (AA)",
                "ALTABA INC (AABA)",
                "AAC HOLDINGS INC (AAC)",
                "ADVISORSHARES DORSEY WRIGHT (AADR)",
                "AMERICAN AIRLINES GROUP INC (AAL)",
                "ALTISOURCE ASSET MANAGEMENT (AAMC)",
                "ATLANTIC AMERICAN CORP (AAME)",
                "AARON'S INC (AAN)",
                "APPLIED OPTOELECTRONICS INC (AAOI)",
                "AAON INC (AAON)",
                "ADVANCE AUTO PARTS INC (AAP)",
                "APPLE INC (AAPL)",
                "AMERICAN ASSETS TRUST INC (AAT)",
                "ALMADEN MINERALS LTD - B (AAU)",
                "ADVANTAGE OIL & GAS LTD (AAV)",
                "ATLAS AIR WORLDWIDE HOLDINGS (AAWW)",
                "ISHARES MSCI ALL COUNTRY ASI (AAXJ)",
                "AXON ENTERPRISE INC (AAXN)",
                "ALLIANCEBERNSTEIN HOLDING LP (AB)",
                "RENMIN TIANLI GROUP INC (ABAC)",
                "ABAXIS INC (ABAX)",
                "ABB LTD-SPON ADR (ABB)",
                "ABBVIE INC (ABBV)",
                "AMERISOURCEBERGEN CORP (ABC)",
                "AMERIS BANCORP (ABCB)",
                "CAMBIUM LEARNING GROUP INC (ABCD)",
                "ALCENTRA CAPITAL CORP (ABDC)",
                "ABERDEEN EMERGING MARKETS SM (ABE)",
                "ABEONA THERAPEUTICS INC (ABEO)",
                "AMBEV SA-ADR (ABEV)",
                "ASBURY AUTOMOTIVE GROUP (ABG)",
                "ABILITY INC (ABIL)",
                "ARCA BIOPHARMA INC (ABIO)",
                "ABLYNX NV - SPONSORED ADR (ABLX)",
                "ABM INDUSTRIES INC (ABM)",
                "ABIOMED INC (ABMD)",
                "ARBOR REALTY TRUST INC (ABR)",
                "ABBOTT LABORATORIES (ABT)",
                "ALLEGIANCE BANCSHARES INC (ABTX)",
                "ARBUTUS BIOPHARMA CORP (ABUS)",
                "BARRICK GOLD CORP (ABX)",
                "ASSOCIATED CAPITAL GROUP - A (AC)",
                "ACADIA PHARMACEUTICALS INC (ACAD)",
                "ATLANTIC CAPITAL BANCSHARES (ACBI)",
                "AMERICAN CAMPUS COMMUNITIES (ACC)",
                "ACCO BRANDS CORP (ACCO)",
                "ACER THERAPEUTICS INC (ACER)",
                "ACETO CORP (ACET)",
                "ATLANTIC COAST FINANCIAL COR (ACFC)",
                "ARCH CAPITAL GROUP LTD (ACGL)",
                "ALUMINUM CORP OF CHINA-ADR (ACH)",
                "ACADIA HEALTHCARE CO INC (ACHC)",
                "ACHILLION PHARMACEUTICALS (ACHN)",
                "ACHIEVE LIFE SCIENCES INC (ACHV)",
                "ACACIA COMMUNICATIONS INC (ACIA)",
                "SPDR MSCI ACWI IMI ETF (ACIM)",
                "AC IMMUNE SA (ACIU)",
                "ACI WORLDWIDE INC (ACIW)",
                "AXCELIS TECHNOLOGIES INC (ACLS)",
                "AECOM (ACM)",
                "ACM RESEARCH INC-CLASS A (ACMR)",
                "ACCENTURE PLC-CL A (ACN)",
                "ACNB CORP (ACNB)",
                "ACORDA THERAPEUTICS INC (ACOR)",
                "ABERDEEN INCOME CREDIT STRAT (ACP)",
                "ARES COMMERCIAL REAL ESTATE (ACRE)",
                "ACLARIS THERAPEUTICS INC (ACRS)",
                "ACELRX PHARMACEUTICALS INC (ACRX)",
                "AMERICAN CAPITAL SENIOR FLOA (ACSF)",
                "AMERICAN CUST SAT CORE ALPHA (ACSI)",
                "ACASTI PHARMA INC (ACST)",
                "ADVISORSHARES VICE ETF (ACT)",
                "ACACIA RESEARCH CORP (ACTG)",
                "ACME UNITED CORP (ACU)",
                "ALLIANZGI DIVERSIFIED INCOME (ACV)",
                "ISHARES EDGE MSCI MULTIFACTO (ACWF)",
                "ISHARES MSCI ACWI ETF (ACWI)",
                "ISHARES EDGE MSCI MIN VOL GL (ACWV)",
                "ISHARES MSCI ACWI EX US ETF (ACWX)",
                "ACXIOM CORP (ACXM)",
                "AEROCENTURY CORP (ACY)",
                "ADAPTIMMUNE THERAPEUTICS-ADR (ADAP)",
                "ADOBE SYSTEMS INC (ADBE)",
                "AGREE REALTY CORP (ADC)",
                "ADVANCED EMISSIONS SOLUTIONS (ADES)",
                "ANALOG DEVICES INC (ADI)",
                "ARCHER-DANIELS-MIDLAND CO (ADM)",
                "ADMA BIOLOGICS INC (ADMA)",
                "ADAMIS PHARMACEUTICALS CORP (ADMP)",
                "ADAMAS PHARMACEUTICALS INC (ADMS)",
                "ADIENT PLC (ADNT)",
                "ADOMANI INC (ADOM)",
                "AUTOMATIC DATA PROCESSING (ADP)",
                "BLDRS ASIA 50 ADR INDEX FUND (ADRA)",
                "BLDRS DEV MKTS 100 ADR INDEX (ADRD)",
                "BLDRS EMER MKTS 50 ADR INDEX (ADRE)",
                "ADURO BIOTECH INC (ADRO)",
                "BLDRS EUROPE 100 ADR INDEX (ADRU)",
                "ALLIANCE DATA SYSTEMS CORP (ADS)",
                "AUTODESK INC (ADSK)",
                "ADVANCED DISPOSAL SERVICES I (ADSW)",
                "ADT INC (ADT)",
                "ADTRAN INC (ADTN)",
                "ADDUS HOMECARE CORP (ADUS)",
                "ADVERUM BIOTECHNOLOGIES INC (ADVM)",
                "ADAMS DIVERSIFIED EQUITY (ADX)",
                "ADVAXIS INC (ADXS)",
                "DB AGRICULTURE SHORT ETN (ADZ)",
                "ADAMS RESOURCES & ENERGY INC (AE)",
                "AMEREN CORPORATION (AEE)",
                "AEGON N.V.-NY REG SHR (AEG)",
                "AEGION CORP (AEGN)",
                "AEHR TEST SYSTEMS (AEHR)",
                "ADVANCED ENERGY INDUSTRIES (AEIS)",
                "AMERICAN EQUITY INVT LIFE HL (AEL)",
                "AGNICO EAGLE MINES LTD (AEM)",
                "AETHLON MEDICAL INC (AEMD)",
                "AMERICAN EAGLE OUTFITTERS (AEO)",
                "AMERICAN ELECTRIC POWER (AEP)",
                "AERCAP HOLDINGS NV (AER)",
                "AERIE PHARMACEUTICALS INC (AERI)",
                "AES CORP (AES)",
                "AETNA INC (AET)",
                "AMERICAN ELECTRIC TECHNOLOGI (AETI)",
                "ADDVANTAGE TECHNOLOGIES GRP (AEY)",
                "AETERNA ZENTARIS INC (AEZS)",
                "ALLIANCE NATIONAL MUNI INC (AFB)",
                "AMERICAN FINANCIAL GROUP INC (AFG)",
                "ATLAS FINANCIAL HOLDINGS INC (AFH)",
                "ARMSTRONG FLOORING INC (AFI)",
                "VANECK VECTORS AFRICA INDEX (AFK)",
                "AFLAC INC (AFL)",
                "AFFIMED NV (AFMD)",
                "AMTRUST FINANCIAL SERVICES (AFSI)",
                "APOLLO SENIOR FLOATING RATE (AFT)",
                "CSOP FTSE CHINA A50 ETF (AFTY)",
                "FIRST MAJESTIC SILVER CORP (AG)",
                "ADVENT CLAYMORE CONVERTIBLE (AGC)",
                "AGCO CORP (AGCO)",
                "ALPINE GLOBAL DYNAMIC DIVIDE (AGD)",
                "AGENUS INC (AGEN)",
                "DB AGRICULTURE LONG ETN (AGF)",
                "AGROFRESH SOLUTIONS INC (AGFS)",
                "ISHARES CORE U.S. AGGREGATE (AGG)",
                "IQ ENHANCED CORE BOND US ETF (AGGE)",
                "IQ ENHANCED CORE PLUS BOND U (AGGP)",
                "WISDOMTREE BARCLAYS YIELD EN (AGGY)",
                "ALAMOS GOLD INC-CLASS A (AGI)",
                "ARGO GROUP INTERNATIONAL (AGII)",
                "AGIOS PHARMACEUTICALS INC (AGIO)",
                "AEGLEA BIOTHERAPEUTICS INC (AGLE)",
                "FEDERAL AGRIC MTG CORP-CL C (AGM)",
                "FEDERAL AGRIC MTG CORP-CL A (AGM.A)",
                "AGM GROUP HOLDINGS INC (AGMH)",
                "ALLERGAN PLC (AGN)",
                "AGNC INVESTMENT CORP (AGNC)",
                "WISDOMTREE BARCLAYS NEGATIVE (AGND)",
                "ASSURED GUARANTY LTD (AGO)",
                "PROSHARES ULTRA SILVER (AGQ)",
                "AVANGRID INC (AGR)",
                "ADECOAGRO SA (AGRO)",
                "AGILE THERAPEUTICS INC (AGRX)",
                "PLAYAGS INC (AGS)",
                "ISHARES ARGENTINA (AGT)",
                "APPLIED GENETIC TECHNOLOGIES (AGTC)",
                "ARGAN INC (AGX)",
                "AGILYSYS INC (AGYS)",
                "ISHARES AGENCY BOND ETF (AGZ)",
                "WISDOMTREE BARCLAYS INTEREST (AGZD)",
                "A H BELO CORP-A (AHC)",
                "ALLIANCE HOLDINGS GP LP (AHGP)",
                "ARMADA HOFFLER PROPERTIES IN (AHH)",
                "ASPEN INSURANCE HOLDINGS LTD (AHL)",
                "AVISTA HEALTHCARE PUBLIC-A (AHPA)",
                "AVISTA HEALTHCARE PUBLIC ACQ (AHPAU)",
                "ALLIED HEALTHCARE PRODUCTS (AHPI)",
                "ASHFORD HOSPITALITY TRUST (AHT)",
                "ARLINGTON ASSET INVESTMENT-A (AI)",
                "ISHARES ASIA 50 ETF (AIA)",
                "AI POWERED EQUITY ETF (AIEQ)",
                "APOLLO TACTICAL INCOME FUND (AIF)",
                "AMERICAN INTERNATIONAL GROUP (AIG)",
                "SENMIAO TECHNOLOGY LTD (AIHS)",
                "ALTRA INDUSTRIAL MOTION CORP (AIMC)",
                "AIMMUNE THERAPEUTICS INC (AIMT)",
                "ALBANY INTL CORP-CL A (AIN)",
                "ASHFORD INC (AINC)",
                "APOLLO INVESTMENT CORP (AINV)",
                "PRECISION THERAPEUTICS INC (AIPT)",
                "AAR CORP (AIR)",
                "AIRGAIN INC (AIRG)",
                "AIR INDUSTRIES GROUP (AIRI)",
                "FIRST TRUST RBA AMERICAN IND (AIRR)",
                "AIR T INC (AIRT)",
                "APPLIED INDUSTRIAL TECH INC (AIT)",
                "APARTMENT INVT & MGMT CO -A (AIV)",
                "ASSURANT INC (AIZ)",
                "ARTHUR J GALLAGHER & CO (AJG)",
                "AEROJET ROCKETDYNE HOLDINGS (AJRD)",
                "GREAT AJAX CORP (AJX)",
                "AKAMAI TECHNOLOGIES INC (AKAM)",
                "ACHAOGEN INC (AKAO)",
                "AKEBIA THERAPEUTICS INC (AKBA)",
                "AKCEA THERAPEUTICS INC (AKCA)",
                "AKERS BIOSCIENCES INC (AKER)",
                "ASANKO GOLD INC (AKG)",
                "EMBOTELLADORA ANDINA-ADR A (AKO.A)",
                "EMBOTELLADORA ANDINA-ADR B (AKO.B)",
                "ALLIANCE CALIF MUNI INCOME (AKP)",
                "ACADIA REALTY TRUST (AKR)",
                "AKORN INC (AKRX)",
                "AK STEEL HOLDING CORP (AKS)",
                "AKOUSTIS TECHNOLOGIES INC (AKTS)",
                "AKARI THERAPEUTICS PLC-ADR (AKTX)",
                "AIR LEASE CORP (AL)",
                "ALBEMARLE CORP (ALB)",
                "ALBIREO PHARMA INC (ALBO)",
                "ALICO INC (ALCO)",
                "WISDOMTREE ASIA LOCAL DEBT (ALD)",
                "ALDER BIOPHARMACEUTICALS INC (ALDR)",
                "ALDEYRA THERAPEUTICS INC (ALDX)",
                "ALLETE INC (ALE)",
                "ALEXANDER & BALDWIN INC (ALEX)",
                "ALPHACLONE ALTERNATIVE ALPHA (ALFA)",
                "ALPHACLONE INTERNATIONAL ETF (ALFI)",
                "ALAMO GROUP INC (ALG)",
                "ALIGN TECHNOLOGY INC (ALGN)",
                "ALLEGIANT TRAVEL CO (ALGT)",
                "ALIMERA SCIENCES INC (ALIM)",
                "ALJ REGIONAL HOLDINGS INC (ALJJ)",
                "ALASKA AIR GROUP INC (ALK)",
                "ALKERMES PLC (ALKS)",
                "ALLSTATE CORP (ALL)",
                "ALLEGION PLC (ALLE)",
                "ALLOT COMMUNICATIONS LTD (ALLT)",
                "ALLY FINANCIAL INC (ALLY)",
                "AMERICAN LORAIN CORP (ALN)",
                "ALLENA PHARMACEUTICALS INC (ALNA)",
                "ALNYLAM PHARMACEUTICALS INC (ALNY)",
                "ALIO GOLD INC (ALO)",
                "ANALOGIC CORP (ALOG)",
                "ASTRONOVA INC (ALOT)",
                "ALPINE IMMUNE SCIENCES INC (ALPN)",
                "ALLIQUA BIOMEDICAL INC (ALQA)",
                "ALARM.COM HOLDINGS INC (ALRM)",
                "AILERON THERAPEUTICS INC (ALRN)",
                "ALASKA COMM SYSTEMS GROUP (ALSK)",
                "ALLISON TRANSMISSION HOLDING (ALSN)",
                "ALTIMMUNE INC (ALT)",
                "ALTAIR ENGINEERING INC - A (ALTR)",
                "PRSHR MRNGSTR ALT SOL ETF (ALTS)",
                "GLOBAL X SUPERDIVIDEND ALTER (ALTY)",
                "AUTOLIV INC (ALV)",
                "ALEXANDER'S INC (ALX)",
                "ALEXION PHARMACEUTICALS INC (ALXN)",
                "ALZHEON INC (ALZH)",
                "ANTERO MIDSTREAM PARTNERS LP (AM)",
                "AMAG PHARMACEUTICALS INC (AMAG)",
                "APPLIED MATERIALS INC (AMAT)",
                "AMBARELLA INC (AMBA)",
                "AMBAC FINANCIAL GROUP INC (AMBC)",
                "AMBER ROAD INC (AMBR)",
                "AMC ENTERTAINMENT HLDS-CL A (AMC)",
                "ISHARES RUSSELL 1000 US REV (AMCA)",
                "AIRMEDIA GROUP INC-ADR (AMCN)",
                "AMC NETWORKS INC-A (AMCX)",
                "ADVANCED MICRO DEVICES (AMD)",
                "AMEDICA CORP (AMDA)",
                "AMETEK INC (AME)",
                "AMEDISYS INC (AMED)",
                "APOLLO MEDICAL HOLDINGS INC (AMEH)",
                "AFFILIATED MANAGERS GROUP (AMG)",
                "AMGEN INC (AMGN)",
                "ANTERO MIDSTREAM GP LP (AMGP)",
                "AMERICAN HOMES 4 RENT- A (AMH)",
                "AMERICAN MIDSTREAM PARTNERS (AMID)",
                "JPMORGAN ALERIAN MLP INDEX (AMJ)",
                "X-LINKS 2XLEVRG ALERIAN MLP (AMJL)",
                "AMKOR TECHNOLOGY INC (AMKR)",
                "ALERIAN MLP ETF (AMLP)",
                "ALLIANCE MMA INC (AMMA)",
                "AMN HEALTHCARE SERVICES INC (AMN)",
                "AMER NATL BNKSHS/DANVILLE VA (AMNB)",
                "ALLIED MOTION TECHNOLOGIES (AMOT)",
                "AMERICA MOVIL-ADR SERIES A (AMOV)",
                "AMERIPRISE FINANCIAL INC (AMP)",
                "AMPIO PHARMACEUTICALS INC (AMPE)",
                "AMPHASTAR PHARMACEUTICALS IN (AMPH)",
                "ALTA MESA RESOURCES INC (AMR)",
                "AMERICAN RIVER BANKSHRS (CA) (AMRB)",
                "AMERESCO INC-CL A (AMRC)",
                "AMERI HOLDINGS INC (AMRH)",
                "A-MARK PRECIOUS METALS INC (AMRK)",
                "AMARIN CORP PLC -ADR (AMRN)",
                "AMYRIS INC (AMRS)",
                "AMERICAN SHARED HOSPITAL SER (AMS)",
                "AMERICAN SUPERCONDUCTOR CORP (AMSC)",
                "AMERISAFE INC (AMSF)",
                "AMERICAN SOFTWARE INC-CL A (AMSWA)",
                "AMERICAN TOWER CORP (AMT)",
                "TD AMERITRADE HOLDING CORP (AMTD)",
                "AEMETIS INC (AMTX)",
                "ETRACS ALERIAN MLP ETN (AMU)",
                "ETRACS ALERIAN MLP IND SER B (AMUB)",
                "AMERICAN WOODMARK CORP (AMWD)",
                "AMERICA MOVIL-SPN ADR CL L (AMX)",
                "INFRACAP MLP ETF (AMZA)",
                "AMAZON.COM INC (AMZN)",
                "AUTONATION INC (AN)",
                "ANAPTYSBIO INC (ANAB)",
                "AMERICAN NATIONAL INSURANCE (ANAT)",
                "ANCHOR BANCORP (ANCB)",
                "ACCESS NATIONAL CORP (ANCX)",
                "ANDERSONS INC/THE (ANDE)",
                "ANDEAVOR (ANDV)",
                "ANDEAVOR LOGISTICS LP (ANDX)",
                "ARISTA NETWORKS INC (ANET)",
                "ABERCROMBIE & FITCH CO-CL A (ANF)",
                "AMIRA NATURE FOODS LTD (ANFI)",
                "ANGI HOMESERVICES INC- A (ANGI)",
                "VANECK VECTORS FALLEN ANGEL (ANGL)",
                "ANGIODYNAMICS INC (ANGO)",
                "ANWORTH MORTGAGE ASSET CORP (ANH)",
                "ANIKA THERAPEUTICS INC (ANIK)",
                "ANI PHARMACEUTICALS INC (ANIP)",
                "ANSYS INC (ANSS)",
                "ANTHERA PHARMACEUTICALS INC (ANTH)",
                "ANTHEM INC (ANTM)",
                "AEGEAN MARINE PETROLEUM NETW (ANW)",
                "SPHERE 3D CORP (ANY)",
                "ISHARES CORE AGGRESSIVE ALLO (AOA)",
                "AMERICAN OUTDOOR BRANDS CORP (AOBC)",
                "ALPINE TOTAL DYNAMIC DIVIDEN (AOD)",
                "ALLIANCE ONE INTERNATIONAL (AOI)",
                "ISHARES CORE CONSERVATIVE AL (AOK)",
                "ISHARES CORE MODERATE ALLOCA (AOM)",
                "AON PLC (AON)",
                "ISHARES CORE GROWTH ALLOCATI (AOR)",
                "SMITH (A.O.) CORP (AOS)",
                "ALPHA & OMEGA SEMICONDUCTOR (AOSL)",
                "AMPCO-PITTSBURGH CORP (AP)",
                "APACHE CORP (APA)",
                "ARTISAN PARTNERS ASSET MA -A (APAM)",
                "ASIA PACIFIC FUND INC (APB)",
                "ANADARKO PETROLEUM CORP (APC)",
                "AIR PRODUCTS & CHEMICALS INC (APD)",
                "APPLIED DNA SCIENCES INC (APDN)",
                "AMERICAN PUBLIC EDUCATION (APEI)",
                "APOLLO ENDOSURGERY INC (APEN)",
                "MORGAN STANLEY ASIA PACIFIC (APF)",
                "AMPHENOL CORP-CL A (APH)",
                "AMPLIPHI BIOSCIENCES CORP (APHB)",
                "APPLE HOSPITALITY REIT INC (APLE)",
                "ARCHROCK PARTNERS LP (APLP)",
                "APELLIS PHARMACEUTICALS INC (APLS)",
                "APOLLO GLOBAL MANAGEMENT - A (APO)",
                "APOGEE ENTERPRISES INC (APOG)",
                "CELLECT BIOTECHNOLOGY LT-ADR (APOP)",
                "APPFOLIO INC - A (APPF)",
                "APPIAN CORP (APPN)",
                "DIGITAL TURBINE INC (APPS)",
                "APRICUS BIOSCIENCES INC (APRI)",
                "BLUE APRON HOLDINGS INC-A (APRN)",
                "ALPHA PRO TECH LTD (APT)",
                "APPTIO INC - CLASS A (APTI)",
                "APTOSE BIOSCIENCES INC (APTO)",
                "PREFERRED APARTMENT COMMUN-A (APTS)",
                "APTIV PLC (APTV)",
                "AMERIGAS PARTNERS-LP (APU)",
                "APTEVO THERAPEUTICS INC (APVO)",
                "ASIA PACIFIC WIRE & CABLE (APWC)",
                "AQUANTIA CORP (AQ)",
                "AQUABOUNTY TECHNOLOGIES (AQB)",
                "AQUA METALS INC (AQMS)",
                "ALGONQUIN POWER & UTILITIES (AQN)",
                "EVOQUA WATER TECHNOLOGIES CO (AQUA)",
                "AQUINOX PHARMACEUTICALS INC (AQXP)",
                "ANTERO RESOURCES CORP (AR)",
                "AMERICAN RENAL ASSOCIATES HO (ARA)",
                "ACCURAY INC (ARAY)",
                "ARC DOCUMENT SOLUTIONS INC (ARC)",
                "ARCBEST CORP (ARCB)",
                "ARES CAPITAL CORP (ARCC)",
                "ARCH COAL INC - A (ARCH)",
                "APPLIANCE RECYCLING CTRS AMR (ARCI)",
                "ARROW RESERVE CAP MANAG ETF (ARCM)",
                "ARCOS DORADOS HOLDINGS INC-A (ARCO)",
                "ARCTURUS THERAPEUTICS LTD (ARCT)",
                "ARC GROUP WORLDWIDE INC (ARCW)",
                "ARDAGH GROUP SA (ARD)",
                "ARES DYNAMIC CREDIT ALLOCATI (ARDC)",
                "ARADIGM CORP (ARDM)",
                "ARDELYX INC (ARDX)",
                "ALEXANDRIA REAL ESTATE EQUIT (ARE)",
                "ARES MANAGEMENT LP (ARES)",
                "APPROACH RESOURCES INC (AREX)",
                "ARGOS THERAPEUTICS INC (ARGS)",
                "GLOBAL X MSCI ARGENTINA ETF (ARGT)",
                "ARGENX SE - ADR (ARGX)",
                "APOLLO COMMERCIAL REAL ESTAT (ARI)",
                "AMERICAN RAILCAR INDUSTRIES (ARII)",
                "ARK GENOMIC REVL MULT-SECTOR (ARKG)",
                "ARK INNOVATION ETF (ARKK)",
                "ARK INDUSTRIAL INNOVATION (ARKQ)",
                "ARK RESTAURANTS CORP (ARKR)",
                "ARK WEB X.O ETF (ARKW)",
                "AMERICAN REALTY INVESTORS IN (ARL)",
                "ALLIANCE RESOURCE PARTNERS (ARLP)",
                "ARALEZ PHARMACEUTICALS INC (ARLZ)",
                "ARAMARK (ARMK)",
                "ARMO BIOSCIENCES INC (ARMO)",
                "ARENA PHARMACEUTICALS INC (ARNA)",
                "ARCONIC INC (ARNC)",
                "ARCHROCK INC (AROC)",
                "ARROW FINANCIAL CORP (AROW)",
                "ARQULE INC (ARQL)",
                "ARMOUR RESIDENTIAL REIT INC (ARR)",
                "ARRIS INTERNATIONAL PLC (ARRS)",
                "ARRAY BIOPHARMA INC (ARRY)",
                "ARTESIAN RESOURCES CORP-CL A (ARTNA)",
                "ART'S-WAY MANUFACTURING CO (ARTW)",
                "AROTECH CORP (ARTX)",
                "ARROW ELECTRONICS INC (ARW)",
                "ARROWHEAD PHARMACEUTICALS IN (ARWR)",
                "ASA GOLD AND PRECIOUS METALS (ASA)",
                "ASSOCIATED BANC-CORP (ASB)",
                "ARDMORE SHIPPING CORP (ASC)",
                "ASCENT CAPITAL GROUP INC-A (ASCMA)",
                "GLOBAL X FTSE SOUTHEAST ASIA (ASEA)",
                "FLEXSHARES REAL ASSETS FUND (ASET)",
                "ASTA FUNDING INC (ASFI)",
                "LIBERTY ALL-STAR GROWTH FD (ASG)",
                "ASGN INC (ASGN)",
                "ASHLAND GLOBAL HOLDINGS INC (ASH)",
                "XTRACKERS HARVEST CSI 300 CH (ASHR)",
                "XTRACKERS HARVEST CSI 500 CH (ASHS)",
                "XTRACKERS CSI 300 CHINA A-SH (ASHX)",
                "ADVANSIX INC (ASIX)",
                "AVINO SILVER & GOLD MINES (ASM)",
                "ASSEMBLY BIOSCIENCES INC (ASMB)",
                "ASML HOLDING NV-NY REG SHS (ASML)",
                "ASCENA RETAIL GROUP INC (ASNA)",
                "ASCENDIS PHARMA A/S - ADR (ASND)",
                "ARSANIS INC (ASNS)",
                "ASPEN AEROGELS INC (ASPN)",
                "ALTISOURCE PORTFOLIO SOL (ASPS)",
                "ASPEN GROUP INC (ASPU)",
                "GRUPO AEROPORTUARIO SUR-ADR (ASR)",
                "AMERISERV FINANCIAL INC (ASRV)",
                "ASTERIAS BIOTHERAPEUTICS (AST)",
                "ASTROTECH CORP (ASTC)",
                "ASTEC INDUSTRIES INC (ASTE)",
                "ASURE SOFTWARE INC (ASUR)",
                "ASV HOLDINGS INC (ASV)",
                "ASE INDUSTRIAL HLDG-ADR-W/I (ASX#)",
                "AMTECH SYSTEMS INC (ASYS)",
                "ATLANTIC POWER CORP (AT)",
                "ATLANTIC ACQUISITION CORP (ATAC)",
                "ATLANTIC ACQUISITION COR-RTS (ATACR)",
                "ATLANTIC ACQUISITION COR - U (ATACU)",
                "ATA INC-ADR (ATAI)",
                "AMERICA FIRST MULTIFAMILY IN (ATAX)",
                "ALPHATEC HOLDINGS INC (ATEC)",
                "A10 NETWORKS INC (ATEN)",
                "ADTALEM GLOBAL EDUCATION INC (ATGE)",
                "ATHENE HOLDING LTD-CLASS A (ATH)",
                "AUTOHOME INC-ADR (ATHM)",
                "ATHENAHEALTH INC (ATHN)",
                "ATHERSYS INC (ATHX)",
                "ALLEGHENY TECHNOLOGIES INC (ATI)",
                "ATKORE INTERNATIONAL GROUP I (ATKR)",
                "ATLANTICUS HOLDINGS CORP (ATLC)",
                "AMES NATIONAL CORP (ATLO)",
                "BARCLAYS ETN+ SELECT MLP ETN (ATMP)",
                "ATN INTERNATIONAL INC (ATNI)",
                "ACTINIUM PHARMACEUTICALS INC (ATNM)",
                "ATHENEX INC (ATNX)",};
        final AutoCompleteTextView autoCompleteTextViewCompany = (AutoCompleteTextView)rootView.findViewById(R.id.companies_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, company_list );

        autoCompleteTextViewCompany.setAdapter(adapter);
        autoCompleteTextViewCompany.setThreshold(1);


        autoCompleteTextViewCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoCompleteTextViewCompany.showDropDown();
            }
        });
/*
        btnSelectedCountry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String country=autoCompleteTextViewCountry.getText().toString();
                textViewSelectedCountry.setText("Selected Country: "+country);
            }
        });
        */
        return rootView;
    }
    void startCreateStockAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.iextrading.com/1.0/stock/market/batch?symbols=dia,ivv,oneq,goog,aapl,msft,eem,efa,spy&types=quote",

                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            try {
                                initialProcessing(response.toString());

                                //Dow Jones Industrial Average
                                TextView diaSym = (TextView) getView().findViewById(R.id.diaSym);
                                diaSym.setText(MyClass.getSymbol(dia));
                                TextView diaComp = (TextView) getView().findViewById(R.id.diaComp);
                                diaComp.setText(MyClass.getCompanyName(dia));
                                TextView diaPrice = (TextView) getView().findViewById(R.id.diaPrice);
                                diaPrice.setText(MyClass.getLatestPrice(dia));
                                TextView diaTime = (TextView) getView().findViewById(R.id.diaTime);
                                diaTime.setText(MyClass.getLatestTime(dia));
                                TextView diaChange = (TextView) getView().findViewById(R.id.diaChange);
                                diaChange.setText(MyClass.getChange(dia));
                                TextView diaChangeP = (TextView) getView().findViewById(R.id.diaChangeP);
                                diaChangeP.setText(MyClass.getChangePercent(dia));

                                //S&P 500
                                TextView ivvSym = (TextView) getView().findViewById(R.id.ivvSym);
                                ivvSym.setText(MyClass.getSymbol(ivv));
                                TextView ivvComp = (TextView) getView().findViewById(R.id.ivvComp);
                                ivvComp.setText(MyClass.getCompanyName(ivv));
                                TextView ivvPrice = (TextView) getView().findViewById(R.id.ivvPrice);
                                ivvPrice.setText(MyClass.getLatestPrice(ivv));
                                TextView ivvTime = (TextView) getView().findViewById(R.id.ivvTime);
                                ivvTime.setText(MyClass.getLatestTime(ivv));
                                TextView ivvChange = (TextView) getView().findViewById(R.id.ivvChange);
                                ivvChange.setText(MyClass.getChange(ivv));
                                TextView ivvChangeP = (TextView) getView().findViewById(R.id.ivvChangeP);
                                ivvChangeP.setText(MyClass.getChangePercent(ivv));

                                //Alphabet Inc.
                                TextView googSym = (TextView) getView().findViewById(R.id.googSym);
                                googSym.setText(MyClass.getSymbol(goog));
                                TextView googComp = (TextView) getView().findViewById(R.id.googComp);
                                googComp.setText(MyClass.getCompanyName(goog));
                                TextView googPrice = (TextView) getView().findViewById(R.id.googPrice);
                                googPrice.setText(MyClass.getLatestPrice(goog));
                                TextView googTime = (TextView) getView().findViewById(R.id.googTime);
                                googTime.setText(MyClass.getLatestTime(goog));
                                TextView googChange = (TextView) getView().findViewById(R.id.googChange);
                                googChange.setText(MyClass.getChange(goog));
                                TextView googChangeP = (TextView) getView().findViewById(R.id.googChangeP);
                                googChangeP.setText(MyClass.getChangePercent(goog));

                                //NASDAQ
                                TextView oneqSym = (TextView) getView().findViewById(R.id.oneqSym);
                                oneqSym.setText(MyClass.getSymbol(oneq));
                                TextView oneqComp = (TextView) getView().findViewById(R.id.oneqComp);
                                oneqComp.setText(MyClass.getCompanyName(oneq));
                                TextView oneqPrice = (TextView) getView().findViewById(R.id.oneqPrice);
                                oneqPrice.setText(MyClass.getLatestPrice(oneq));
                                TextView oneqTime = (TextView) getView().findViewById(R.id.oneqTime);
                                oneqTime.setText(MyClass.getLatestTime(oneq));
                                TextView oneqChange = (TextView) getView().findViewById(R.id.oneqChange);
                                oneqChange.setText(MyClass.getChange(oneq));
                                TextView oneqChangeP = (TextView) getView().findViewById(R.id.oneqChangeP);
                                oneqChangeP.setText(MyClass.getChangePercent(oneq));



                                Log.d(TAG, response.toString(2));




                            } catch (JSONException ignored) { }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialProcessing(final String json) {
        JsonParser parser = new JsonParser();
        JsonObject response = parser.parse(json).getAsJsonObject();
        dia = response.get("DIA").getAsJsonObject();
        ivv = response.get("IVV").getAsJsonObject();
        oneq = response.get("ONEQ").getAsJsonObject();
        goog = response.get("GOOG").getAsJsonObject();
        aapl = response.get("AAPL").getAsJsonObject();
        msft = response.get("MSFT").getAsJsonObject();
        eem = response.get("EEM").getAsJsonObject();
        efa = response.get("EFA").getAsJsonObject();
        spy = response.get("SPY").getAsJsonObject();
    }
}
