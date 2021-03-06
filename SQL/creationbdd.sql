create database plateforme;
use plateforme;

SET foreign_key_checks = 0;
drop table Joueur;
drop table Portefeuille;
drop table EstComposeTitre;
drop table EstComposeOption;
drop table EstComposeObligation;
drop table Historique;
drop table Titre;
drop table Obligation;
drop table HistoriquePortefeuille;
SET foreign_key_checks = 1;


SELECT "Suppression tables termine" as "Message";


create table Joueur(
    login varchar(20) not null,
    motDePasse varchar(56) not null,
    
    PRIMARY KEY(login)
);

create table Portefeuille(
    idPortefeuille INTEGER AUTO_INCREMENT not null,
    login varchar(20) not null UNIQUE,
    argentDisponible FLOAT not null,
    rendement FLOAT,

    FOREIGN KEY (login) REFERENCES Joueur(login),

    PRIMARY KEY(idPortefeuille)
);

create table Titre(
    code varchar(20) not null,
    libelle varchar(60) not null,
    type ENUM('ACTION','INDICE') not null,
    rendementDividende FLOAT,
    nombreDisponible INTEGER not null,

    PRIMARY KEY(code)
);

create table Obligation(
    emetteur varchar(40) not null,
    prix FLOAT not null,
    tauxInterets FLOAT not null,
    nombreDisponible INTEGER not null,

    PRIMARY KEY (emetteur)
);

create table Historique(
    code varchar(20) not null,
    dateCours DATE not null,
    valeurOuverture FLOAT not null,
    valeurFermeture FLOAT not null,
    valeurBas FLOAT not null,
    valeurHaut FLOAT not null,
    volume BIGINT not null,
    valeurAjustee FLOAT not null,

    FOREIGN KEY (code) REFERENCES Titre(Code),

    PRIMARY KEY (code, dateCours)
);

create table EstComposeTitre(
    idPortefeuille INTEGER not null,
    code varchar(20) not null,
    quantite INTEGER not null,
    prixUnitaire FLOAT not null,

    FOREIGN KEY (idPortefeuille) REFERENCES Portefeuille(idPortefeuille),
    FOREIGN KEY (code) REFERENCES Titre(code),

    PRIMARY KEY (idPortefeuille,code)
);

create table EstComposeOption(
    idOption INTEGER AUTO_INCREMENT not null,
    type ENUM('CALL','PUT') not null,
    position ENUM('LONG','SHORT') not null,
    maturite DATE not null,
    strike FLOAT not null,
    prime FLOAT not null,
    idPortefeuille INTEGER not null,
    code varchar(20) not null,
    quantite INTEGER not null,

    FOREIGN KEY (idPortefeuille) REFERENCES Portefeuille(idPortefeuille),
    FOREIGN KEY (code) REFERENCES Titre(code),

    PRIMARY KEY (idOption)
);

create table EstComposeObligation(
    idPortefeuille INTEGER not null,
    emetteur varchar(20) not null,
    quantite INTEGER not null,
    dateFin DATE not null,

    FOREIGN KEY (idPortefeuille) REFERENCES Portefeuille(idPortefeuille),
    FOREIGN KEY (emetteur) REFERENCES Obligation(emetteur),

    PRIMARY KEY (idPortefeuille,emetteur,dateFin)
);

create table HistoriquePortefeuille(
    idHistoriquePortefeuille INTEGER AUTO_INCREMENT not null,
    idPortefeuille INTEGER not null,
    code varchar(20) null,
    emetteur varchar(40) null,
    prix FLOAT not null,
    quantite INTEGER not null,
    dateOperation DATE not null,
    dateFinObligation DATE,

    FOREIGN KEY (idPortefeuille) REFERENCES Portefeuille(idPortefeuille),
    FOREIGN KEY (code) REFERENCES Titre(code),
    FOREIGN KEY (emetteur) REFERENCES Obligation(emetteur),

    PRIMARY KEY(idHistoriquePortefeuille)
);

INSERT INTO Obligation
(emetteur, prix, tauxInterets, nombreDisponible) VALUES
    ("FRANCE", 1, 0.029, 100000),
    ("ESPAGNE", 1, 0.045, 1000),
    ("ALLEMAGNE", 1, 0.02, 1000),
    ("ROYAUME-UNI", 1, 0.025, 1000),
    ("RENAULT", 1000, 0.06, 5000000),
    ("TOTAL", 100, 0.042, 1000000),
    ("PEUGEOT", 1000, 0.064, 2000000),
    ("AREVA", 100, 0.045, 10000000);

INSERT INTO Titre
(code, libelle, type, nombreDisponible) VALUES
    ("^FCHI", "CAC40", "INDICE",10000),
    ("^FTSE", "FTSE100", "INDICE", 1000),
    ("^GDAXI", "DAX", "INDICE", 1000),
    ("^DJI", "DOW JONES", "INDICE", 1000),
    ("^NDX", "NASDAQ-100", "INDICE", 1000);
    
INSERT INTO Titre
(code, libelle, type, rendementDividende, nombreDisponible) VALUES
    ("AC.PA","Accor S.A.","ACTION",0.028,1000),
    ("AI.PA","Air Liquide SA","ACTION",0.027,1000),
    ("AIR.PA","AIRBUS GROUP","ACTION",0.01,1000),
    ("ALO.PA","Alstom SA","ACTION",0.013,1000),
    ("MT.PA","ARCELORMITTAL REG","ACTION",0.025,1000),
    ("CS.PA","AXA Group","ACTION",0.042,1000),
    ("BNP.PA","BNP Paribas SA","ACTION",0.033,1000),
    ("EN.PA","Bouygues SA","ACTION",0.045,1000),
    ("CAP.PA","Cap Gemini S.A.","ACTION",0.015,1000),
    ("CA.PA","Carrefour SA","ACTION",0.027,1000),
    ("SGO.PA","Compagnie de Saint-Gobain S.A.","ACTION",0.035,1000),
    ("ML.PA","Compagnie Generale DES Etablissements Michelin SCA","ACTION",0.031,1000),
    ("ACA.PA","Credit Agricole S.A.","ACTION",0.036,1000),
    ("BN.PA","Danone","ACTION",0.025,1000),
    ("ENGI.PA","ENGIE ","ACTION",0.104,1000),
    ("EI.PA","Essilor International SA","ACTION",0.009,1000),
    ("KER.PA","Kering SA","ACTION",0.067,1000),
    ("LI.PA","Klepierre SA","ACTION",0.039,1000),
    ("OR.PA","L'Oreal SA","ACTION",0.018,1000),
    ("LHN.PA","LafargeHolcim' Ltd.","ACTION",0.021,1000),
    ("LR.PA","Legrand SA","ACTION",0.023,1000),
    ("MC.PA","LVMH Moet Hennessy Louis Vuitton SA","ACTION",0.024,1000),
    ("NOKIA.PA","Nokia Corporation","ACTION",0.17,1000),
    ("ORA.PA","Orange","ACTION",0.039,1000),
    ("RI.PA","Pernod-Ricard SA","ACTION",0.018,1000),
    ("UG.PA","Peugeot S.A.","ACTION",0.038,1000),
    ("PUB.PA","Publicis Groupe SA","ACTION",0.023,1000),
    ("RNO.PA","Renault SA","ACTION",0.024,1000),
    ("SAF.PA","Safran SA","ACTION",0.022,1000),
    ("SAN.PA","Sanofi","ACTION",0.039,1000),
    ("SU.PA","Schneider Electric SE","ACTION",0.04,1000),
    ("GLE.PA","Societe Generale Group","ACTION",0.033,1000),
    ("TEC.PA","Technip SA","ACTION",0.05,1000),
    ("FP.PA","TOTAL S.A.","ACTION",0.065,1000),
    ("FR.PA","Valeo SA","ACTION",0.129,1000),
    ("VIE.PA","Veolia Environnement S.A.","ACTION",0.033,1000),
    ("DG.PA","VINCI S.A.","ACTION",0.031,1000),
    ("VIV.PA","Vivendi SA","ACTION",0.106,1000),
    ("III.L","3i Ord","ACTION",0.012,1000),
    ("ADN.L","Aberdeen Asset Management PLC","ACTION",0.013,1000),
    ("ADM.L","Admiral Group plc","ACTION",0.029,1000),
    ("AAL.L","Anglo American plc","ACTION",0.246,1000),
    ("ANTO.L","Antofagasta plc","ACTION",0.037,1000),
    ("ARM.L","ARM Holdings plc","ACTION",0.008,1000),
    ("AHT.L","Ashtead Group plc","ACTION",0.015,1000),
    ("ABF.L","Associated British Foods plc","ACTION",0.012,1000),
    ("AZN.L","AstraZeneca PLC","ACTION",0.043,1000),
    ("AV.L","Aviva plc","ACTION",0.041,1000),
    ("BAB.L","Babcock International Group plc","ACTION",0.02,1000),
    ("BA.L","BAE Systems plc","ACTION",0.015,1000),
    ("BARC.L","Barclays PLC","ACTION",0.034,1000),
    ("BDEV.L","Barratt Developments plc","ACTION",0.058,1000),
    ("BG.L","BG Group plc","ACTION",0.02,1000),
    ("BLT.L","BHP Billiton plc","ACTION",0.134,1000),
    ("BP.L","BP p.l.c.","ACTION",0.078,1000),
    ("BATS.L","British American Tobacco p.l.c.","ACTION",0.042,1000),
    ("BT-A.L","BT Group plc","ACTION",0.028,1000),
    ("BNZL.L","Bunzl plc","ACTION",0.02,1000),
    ("BRBY.L","Burberry Group plc","ACTION",0.032,1000),
    ("CPI.L","Capita plc","ACTION",0.026,1000),
    ("CCL.L","Carnival plc","ACTION",0.023,1000),
    ("CNA.L","Centrica plc","ACTION",0.058,1000),
    ("CCH.L","COCA-COLA HBC N","ACTION",0.072,1000),
    ("CPG.L","Compass Group PLC","ACTION",0.026,1000),
    ("CRH.L","CRH plc","ACTION",0.02,1000),
    ("DCC.L","DCC plc","ACTION",0.019,1000),
    ("DGE.L","Diageo plc","ACTION",0.032,1000),
    ("DLG.L","Direct Line Insurance Group PLC","ACTION",0.052,1000),
    ("DC.L","DIXONS CARPHONE","ACTION",0.03,1000),
    ("EZJ.L","easyJet plc","ACTION",0.008,1000),
    ("EXPN.L","Experian plc","ACTION",0.035,1000),
    ("FRES.L","Fresnillo PLC","ACTION",0.008,1000),
    ("GKN.L","GKN plc","ACTION",0.026,1000),
    ("GSK.L","GlaxoSmithKline plc","ACTION",0.059,1000),
    ("GLEN.L","Glencore Plc","ACTION",0.044,1000),
    ("HMSO.L","Hammerson plc","ACTION",0.033,1000),
    ("HL.L","Hargreaves Lansdown plc","ACTION",0.047,1000),
    ("HIK.L","Hikma Pharmaceuticals PLC","ACTION",0.06,1000),
    ("HSBA.L","HSBC Holdings plc","ACTION",0.068,1000),
    ("IMT.L","Imperial Tobacco Group PLC","ACTION",0.04,1000),
    ("ISAT.L","Inmarsat Plc","ACTION",0.032,1000),
    ("IHG.L","Intercontinental Hotels Group plc","ACTION",0.019,1000),
    ("IAG.L","International Consolidated Airlines Group, S.A.","ACTION",0.01,1000),
    ("ITRK.L","Intertek Group plc","ACTION",0.019,1000),
    ("INTU.L","intu properties plc","ACTION",0.032,1000),
    ("ITV.L","ITV plc","ACTION",0.04,1000),
    ("BKG.L","The Berkeley Group Holdings plc","ACTION",0.05,1000),
    ("BLND.L","The British Land Company PLC","ACTION",0.039,1000),
    ("ADS.DE","Adidas AG","ACTION",0.013,1000),
    ("ALV.DE","Allianz SE","ACTION",0.034,1000),
    ("BAS.DE","BASF SE","ACTION",0.032,1000),
    ("BAYN.DE","Bayer AG","ACTION",0.016,1000),
    ("BMW.DE","Bayerische Motoren Werke Aktiengesellschaft","ACTION",0.027,1000),
    ("BEI.DE","Beiersdorf AG","ACTION",0.007,1000),
    ("CBK.DE","Commerzbank AG","ACTION",0.029,1000),
    ("CON.DE","Continental Aktiengesellschaft","ACTION",0.027,1000),
    ("DAI.DE","Daimler AG","ACTION",0.027,1000),
    ("DBK.DE","Deutsche Bank AG","ACTION",0.029,1000),
    ("DB1.DE","Deutsche Boerse AG","ACTION",0.021,1000),
    ("LHA.DE","Deutsche Lufthansa Aktiengesellschaft","ACTION",0.031,1000),
    ("DPW.DE","Deutsche Post AG","ACTION",0.036,1000),
    ("DTE.DE","Deutsche Telekom AG","ACTION",0.032,1000),
    ("EOAN.DE","E.ON SE","ACTION",0.043,1000),
    ("FME.DE","Fresenius Medical Care AG & Co. KGAA","ACTION",0.008,1000),
    ("FRE.DE","Fresenius SE & Co KGaA","ACTION",0.008,1000),
    ("HEI.DE","HeidelbergCement AG","ACTION",0.016,1000),
    ("HEN3.DE","Henkel AG & Co. KGaA","ACTION",0.01,1000),
    ("IFX.DE","Infineon Technologies AG","ACTION",0.017,1000),
    ("SDF.DE","K+S Aktiengesellschaft","ACTION",0.031,1000),
    ("LIN.DE","Linde Aktiengesellschaft","ACTION",0.019,1000),
    ("MRK.DE","Merck KGaA","ACTION",0.009,1000),
    ("MUV2.DE","Münchener Rückversicherungs-Gesellschaft Aktiengesellschaft","ACTION",0.008,1000),
    ("RWE.DE","RWE AG","ACTION",0.065,1000),
    ("SAP.DE","SAP SE","ACTION",0.011,1000),
    ("SIE.DE","Siemens Aktiengesellschaft","ACTION",0.031,1000),
    ("TKA.DE","ThyssenKrupp AG","ACTION",0.007,1000),
    ("VOW3.DE","Volkswagen AG","ACTION",0.001,1000),
    ("VNA.DE","VONOVIA N","ACTION",0.13,1000),
    ("DIS", "Walt Disney", "Action", 0.0149 ,10000),  
    ("MCD", "Mc Donalds", "Action", 0.0286 ,10000), 
    ("ATVI","Activision Blizzard, Inc.","ACTION",0.059,1000),
    ("ADBE","Adobe Systems Incorporated","ACTION",0,1000),
    ("AKAM","Akamai Technologies, Inc.","ACTION",0,1000),
    ("ALXN","Alexion Pharmaceuticals, Inc.","ACTION",0,1000),
    ("GOOG","Alphabet Inc.","ACTION",0,1000),
    ("GOOGL","Alphabet Inc.","ACTION",0,1000),
    ("AMZN","Amazon.com, Inc.","ACTION",0,1000),
    ("AAL","American Airlines Group Inc.","ACTION",0.0089,1000),
    ("AMGN","Amgen Inc.","ACTION",0.0195,1000),
    ("ADI","Analog Devices, Inc.","ACTION",0.0284,1000),
    ("AAPL","Apple Inc.","ACTION",0.0188,1000),
    ("AMAT","Applied Materials, Inc.","ACTION",0.0214,1000),
    ("ADSK","Autodesk, Inc.","ACTION",0,1000),
    ("ADP","Automatic Data Processing, Inc.","ACTION",0.023,1000),
    ("BIDU","Baidu, Inc.","ACTION",0.013,1000),
    ("BBBY","Bed Bath & Beyond Inc.","ACTION",0,1000),
    ("BIIB","Biogen Inc.","ACTION",0,1000),
    ("BMRN","BioMarin Pharmaceutical Inc.","ACTION",0,1000),
    ("AVGO","Broadcom Limited","ACTION",0.0107,1000),
    ("CA","CA, Inc.","ACTION",0.035,1000),
    ("CELG","Celgene Corporation","ACTION",0.012,1000),
    ("CERN","Cerner Corporation","ACTION",0.021,1000),
    ("CHTR","Charter Communications, Inc.","ACTION",0.,000),
    ("CHKP","Check Point Software Technologies Ltd.","ACTION",0.013,1000),
    ("CSCO","Cisco Systems, Inc.","ACTION",0.0295,1000),
    ("CTXS","Citrix Systems, Inc.","ACTION",0.037,1000),
    ("CTSH","Cognizant Technology Solutions Corporation","ACTION",0.021,1000),
    ("CMCSA","Comcast Corporation","ACTION",0.0177,1000),
    ("COST","Costco Wholesale Corporation","ACTION",0.0403,1000),
    ("CTRP","Ctrip.com International Ltd.","ACTION",0.012,1000),
    ("DISCA","Discovery Communications, Inc.","ACTION",0.003,1000),
    ("DISCK","Discovery Communications, Inc.","ACTION",0.005,1000),
    ("DISH","Dish Network Corp.","ACTION",0.007,1000),
    ("DLTR","Dollar Tree, Inc.","ACTION",0.009,1000),
    ("EBAY","eBay Inc.","ACTION",0.016,1000),
    ("EA","Electronic Arts Inc.","ACTION",0.017,1000),
    ("ENDP","Endo International plc","ACTION",0.022,1000),
    ("EXPE","Expedia Inc.","ACTION",0.0098,1000),
    ("ESRX","Express Scripts Holding Company","ACTION",0,1000),
    ("FB","Facebook, Inc.","ACTION",0,1000),
    ("FAST","Fastenal Company","ACTION",0.0274,1000),
    ("FISV","Fiserv, Inc.","ACTION",0.012,1000),
    ("GILD","Gilead Sciences Inc.","ACTION",0.0127,1000),
    ("HSIC","Henry Schein, Inc.","ACTION",0,1000),
    ("ILMN","Illumina Inc.","ACTION",0.003,1000),
    ("INCY","Incyte Corporation","ACTION",0.007,1000),
    ("INTC","Intel Corporation","ACTION",0.0279,1000),
    ("INTU","Intuit Inc.","ACTION",0.0104,1000),
    ("ISRG","Intuitive Surgical, Inc.","ACTION",0.005,1000),
    ("JD","JD.com, Inc.","ACTION",0.008,1000),
    ("KLAC","KLA-Tencor Corporation","ACTION",0.0288,1000),
    ("LRCX","Lam Research Corporation","ACTION",0.0302,1000),
    ("LBTYA","Liberty Global plc","ACTION",0.0088,1000),
    ("LBTYK","Liberty Global plc","ACTION",0.002,1000),
    ("QVCA","Liberty Interactive Corporation","ACTION",0.003,1000),
    ("LMCA","Liberty Media Corporation","ACTION",0.006,1000),
    ("LMCK","Liberty Media Corporation","ACTION",0.004,1000),
    ("LVNTA","Liberty Ventures","ACTION",0.018,1000),
    ("LLTC","Linear Technology Corporation","ACTION",0.0266,1000),
    ("MAR","Marriott International, Inc.","ACTION",0.0142,1000),
    ("MAT","Mattel, Inc.","ACTION",0.0559,1000),
    ("MXIM","Maxim Integrated Products, Inc.","ACTION",0.0295,1000),
    ("MU","Micron Technology, Inc.","ACTION",0,1000),
    ("MSFT","Microsoft Corporation","ACTION",0.0267,1000),
    ("MDLZ","Mondelez International, Inc.","ACTION",0.0143,1000),
    ("MNST","Monster Beverage Corporation","ACTION",0.005,1000),
    ("MYL","Mylan N.V.","ACTION",0,1000),
    ("NTAP","NetApp, Inc.","ACTION",0.0271,1000),
    ("NFLX","Netflix, Inc.","ACTION",0.0323,1000),
    ("NCLH","Norwegian Cruise Line Holdings Ltd.","ACTION",0,1000),
    ("NVDA","NVIDIA Corporation","ACTION",0.012,1000),
    ("NXPI","NXP Semiconductors NV","ACTION",0.0163,1000),
    ("ORLY","O'Reilly' Automotive Inc.","ACTION",0.001,1000),
    ("PCAR","PACCAR Inc.","ACTION",0.0405,1000),
    ("PAYX","Paychex, Inc.","ACTION",0.0414,1000),
    ("PYPL","PayPal Holdings, Inc.","ACTION",0.0318,1000),
    ("QCOM","QUALCOMM Incorporated","ACTION",0.0367,1000),
    ("REGN","Regeneron Pharmaceuticals, Inc.","ACTION",0.036,1000),
    ("ROST","Ross Stores Inc.","ACTION",0.0097,1000),
    ("SNDK","SanDisk Corp.","ACTION",0.0118,1000),
    ("SBAC","SBA Communications Corp.","ACTION",0.0023,1000),
    ("STX","Seagate Technology Public Limited Company","ACTION",0.0559,1000),
    ("SIRI","Sirius XM Holdings Inc.","ACTION",0.0755,1000),
    ("SWKS","Skyworks Solutions Inc.","ACTION",0.0085,1000),
    ("SBUX","Starbucks Corporation","ACTION",0.0113,1000),
    ("SRCL","Stericycle, Inc.","ACTION",0.0137,1000),
    ("SYMC","Symantec Corporation","ACTION",0.0297,1000),
    ("TMUS","T-Mobile US, Inc.","ACTION",0.0313,1000),
    ("TSLA","Tesla Motors, Inc.","ACTION",0.005,1000),
    ("TXN","Texas Instruments Inc.","ACTION",0.0255,1000),
    ("KHC","The Kraft Heinz Company","ACTION",0.0297,1000),
    ("PCLN","The Priceline Group Inc.","ACTION",0.0089,1000),
    ("TSCO","Tractor Supply Company","ACTION",0.0103,1000),
    ("TRIP","TripAdvisor Inc.","ACTION",0.007,1000),
    ("FOX","Twenty-First Century Fox, Inc.","ACTION",0.015,1000),
    ("FOXA","Twenty-First Century Fox, Inc.","ACTION",0.019,1000),
    ("ULTA","ULTA Salon, Cosmetics & Fragrance, Inc.","ACTION",0.006,1000),
    ("VRSK","Verisk Analytics, Inc.","ACTION",0.023,1000),
    ("VRTX","Vertex Pharmaceuticals Incorporated","ACTION",0.036,1000),
    ("VIAB","Viacom, Inc.","ACTION",0.0355,1000),
    ("VOD","Vodafone Group Plc","ACTION",0.0353,1000),
    ("WBA","Walgreens Boots Alliance, Inc.","ACTION",0.0159,1000),
    ("WDC","Western Digital Corporation","ACTION",0.0284,1000),
    ("WFM","Whole Foods Market, Inc.","ACTION",0.0155,1000),
    ("XLNX","Xilinx Inc.","ACTION",0.0294,1000),
    ("YHOO","Yahoo! Inc.","ACTION",0.0197,1000);
