package com.jphaugla.service.impl;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.jphaugla.domain.*;
import com.jphaugla.repository.MerchantRepository;
import com.jphaugla.repository.TransactionReturnRepository;
import com.jphaugla.repository.PhoneRepository;
import com.jphaugla.repository.EmailRepository;
import com.jphaugla.repository.CustomerRepository;
import com.jphaugla.repository.AccountRepository;

import com.jphaugla.service.DataGeneratorService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {

    @Value("${app.region}")
    private String source_region;
    @Autowired
    MerchantRepository merchantRepository;
    @Autowired
    TransactionReturnRepository transactionReturnRepository;
    @Autowired
    PhoneRepository phoneRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;

    Faker usFaker = new Faker(new Locale("en-US"));

    private static final int HOUR_MILLIS = 1000*60*60;
    private static final int DAY_MILLIS = HOUR_MILLIS * 24;
    private static AtomicInteger accountNoGenerator = new AtomicInteger(1);
    private static List<String> accountTypes = Arrays.asList("Current", "Joint Current", "Saving", "Mortgage",
            "E-Saving", "Deposit");
    private static List<String> accountIds = new ArrayList<String>();

    @Override
    public List<Email> createEmail(Customer customer) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Email home_email = new Email(customer.getFirstName() + "." + customer.getLastName() +
                "@gmail.com","home", customer.getId(), source_region);
        Email work_email = new Email(customer.getFirstName() + "." + customer.getLastName() +
                "@BigCompany.com","work", customer.getId(), source_region);
        List<Email> emailList;
        emailList = new ArrayList<Email>();
        emailList.add(home_email);
        emailList.add(work_email);
        return emailList;
    }
    @Override
    public List<Phone> createPhone(Customer customer) {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Phone home_phone = new Phone(usFaker.numerify("###-###-####"),"home", customer.getId(), source_region);
        Phone cell_phone = new Phone(usFaker.numerify("###-###-####"), "cell", customer.getId(), source_region);
        Phone workPhone = new Phone(usFaker.numerify("###-###-####"), "work", customer.getId(), source_region);
        List<Phone> phoneList;
        phoneList = new ArrayList<Phone>();
        phoneList.add(home_phone);
        phoneList.add(cell_phone);
        phoneList.add(workPhone);
        return phoneList;
    }

    @Override
    public Customer createRandomCustomer(String key_suffix) {

        Customer customer = new Customer();

        customer.setAddressLine1(usFaker.address().streetAddress());
        customer.setCreatedBy("Java Test");
        customer.setLastUpdatedBy("Java Test");
        customer.setCustomerType("Retail");

        customer.setCurrentTime(source_region);

        customer.setCustomerOriginSystem("RCIF");
        customer.setCustomerStatus("A");
        customer.setCountryCode("00");

        int lastDigit = usFaker.number().randomDigit();
        if (lastDigit>7) {
            customer.setAddressLine2("Apt " + lastDigit);
            customer.setAddressType("Apartment");
            customer.setBillPayEnrolled("false");
        }
        else if (lastDigit==3){
            customer.setBillPayEnrolled("false");
            customer.setAddressType("Mobile");
        }
        else {
            customer.setAddressType("Residence");
            customer.setBillPayEnrolled("true");
        }
        customer.setCity(usFaker.address().city());
        String stateAbbreviation = usFaker.address().stateAbbr();
        customer.setStateAbbreviation(stateAbbreviation);
        customer.setDateOfBirth(String.valueOf(usFaker.date().birthday(18,95)));
        Name person=usFaker.name();
        String lastName = person.lastName();
        String firstName = person.firstName();
        String middleName = person.firstName();
        customer.setGender(genderList[lastDigit]);
        if (Objects.equals(genderList[lastDigit], "F")){
            customer.setPrefix("Ms");
        }
        else {
            customer.setPrefix("Mr");
        }
        String zipChar = usFaker.address().zipCodeByState(stateAbbreviation);
        customer.setZipcode(zipChar);
        customer.setZipcode4(zipChar + usFaker.number().digits(4));
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setMiddleName(middleName);
        customer.setFullName(firstName + " " + middleName + " " + lastName);

        return customer;
    }
    @Override
    public List<Merchant> createMerchantList() {
        List<Merchant> merchants = new ArrayList<>();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        int iterations = issuers.length;
        for (int i=0; i < iterations; i++) {
            merchants.add(new Merchant(issuers[i], issuersCD[i], issuersCDdesc[i], States[i],"US",source_region));
        }
        return merchants;
    };
    @Override
    public List<String> createMerchantName() {
        List<String> merchants = new ArrayList<>();
        // for(String merchant:issuers) {
        merchants = Arrays.asList(issuers);
        return merchants;
    }

    @Override
    public List<TransactionReturn> createTransactionReturnList() {
        List<TransactionReturn> transactionReturns = new ArrayList<>();
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        transactionReturns.add(new TransactionReturn("1345","incorrect amount recorded",source_region));
        transactionReturns.add(new TransactionReturn("1554","not authorized transaction",source_region));
        transactionReturns.add(new TransactionReturn("6555","wrong account",source_region));
        return transactionReturns;
    };

    @Override
    public List<Account> createRandomAccountsForCustomer(Customer customer, String key_suffix) {

        int noOfAccounts = Math.random() < .1 ? 4 : 3;
        List<Account> accounts = new ArrayList<Account>();


        for (int i = 0; i < noOfAccounts; i++){

            Account account = new Account();
            String accountNumber = "Acct" + accountNoGenerator.getAndIncrement() + key_suffix;
            // String accountNumber = "Acct" + Integer.toString(i) + key_suffix;
            account.setCardNum( UUID.randomUUID().toString().replace('-','x'));
            account.setCustomerId(customer.getId());
            account.setAccountNo(accountNumber);
            account.setAccountType(accountTypes.get(i));
            account.setAccountStatus("Open");
            account.setLastUpdatedBy("Java Test");
            account.setCurrentTime(source_region);
            account.setCreatedBy("Java Test");
            account.setAccountOriginSystem("RCIF");
            accounts.add(account);

            //Keep a list of all Account Nos to create the transactions
            accountIds.add(account.getAccountNo());
        }

        return accounts;
    }

    @Override
    public Transaction createRandomTransaction(int noOfDays, Integer idx, Account account,
                                               String key_suffix, List<Merchant> merchants,
                                               List<TransactionReturn> transactionReturns) {
        double v = Math.random() * locations.size();
        int intValue = (int) v;
        String location = locations.get(intValue);
        v = Math.ceil(Math.random() * 3);
        int noOfItems = (int) v;
        v = Math.random() * issuers.length;
        int randomLocation = (int) v;

        Long currentMillis = System.currentTimeMillis();
        //  gets random time between current time and requested number of days ago
        Long millisRandom = Long.valueOf(usFaker.random().nextInt(0,noOfDays *DAY_MILLIS));
        //  old data is really null date but can't use null
        Timestamp timestamp_old_data = new Timestamp(0);
        // current timestamp is really already offset by a random number of time in the past based on requested number of days
        Timestamp timestamp_current = new Timestamp(currentMillis-millisRandom);
        //  random time between one day prior to timestamp_current and timestamp_current minus one hour
        Timestamp timestamp_minus_one = new Timestamp(currentMillis-millisRandom-
                usFaker.random().nextInt(HOUR_MILLIS, DAY_MILLIS));
        //  two days prior to timestamp_current
        Timestamp timestamp_minus_two = new Timestamp(currentMillis-millisRandom-
                usFaker.random().nextInt(DAY_MILLIS + HOUR_MILLIS, DAY_MILLIS * 2));

        Transaction transaction = new Transaction();
        createItemsAndAmount(noOfItems, transaction);
        transaction.setAccountId(account.getId());
        String transactionStat = transactionStatus[randomLocation];
        transaction.setStatus(transactionStat);
        transaction.setLastUpdated(timestamp_current);
        if(Objects.equals(transactionStat, "POSTED")) {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            transaction.setPostingDate(timestamp_current);
            transaction.setSettlementDate(timestamp_minus_one);
            transaction.setInitialDate(timestamp_minus_two);
        } else if (Objects.equals(transactionStat, "SETTLED")) {
            transaction.setSettlementDate(timestamp_current);
            transaction.setInitialDate(timestamp_minus_one);
            transaction.setPostingDate(timestamp_old_data);
        } else {
            transaction.setInitialDate(timestamp_current);
            transaction.setPostingDate(timestamp_old_data);
            transaction.setSettlementDate(timestamp_old_data);
        }
        transaction.setLocation(location);
        if(randomLocation<5) {
            transaction.setAmountType("Debit");
        }
        else{
            transaction.setAmountType("Credit");
        }
        transaction.setMerchant(merchants.get(randomLocation).getName());

        transaction.setReferenceKeyType("reftype");
        transaction.setReferenceKeyValue("thisRef");

        transaction.setTranCode(issuersCD[randomLocation]);
        transaction.setDescription("description" + issuersCD[randomLocation] + genderList[randomLocation/3 + 1]);
        if(randomLocation==8) {
            transaction.setTransactionReturn(transactionReturns.get(0).getReasonCode());
        } else if (randomLocation == 13) {
            transaction.setTransactionReturn(transactionReturns.get(1).getReasonCode());
        }
        transaction.setCurrentTime(source_region);
        return transaction;
    }


    @Override
    public List<Account> createCustomerAccount(int noOfCustomers, String key_suffix) throws ExecutionException {

        log.info("Creating " + noOfCustomers + " customers with accounts and suffix " + key_suffix);
        Timer custTimer = new Timer();
        List<Account> accounts = null;
        List<Account> allAccounts = new ArrayList<>();
        List<Email> emails = null;
        List<Phone> phoneNumbers = null;
        int totalAccounts = 0;
        int totalEmails = 0;
        int totalPhone = 0;
        log.info("before the big for loop");
        for (int i = 0; i < noOfCustomers; i++) {
            // log.info("int noOfCustomer for loop i=" + i);
            Customer customer = createRandomCustomer(key_suffix);
            List<Email> emailList = createEmail(customer);
            List<Phone> phoneList = createPhone(customer);
            for (Phone phoneNumber : phoneNumbers = phoneList) {
                phoneRepository.save(phoneNumber);
            }
            totalPhone = totalPhone + phoneNumbers.size();
            for (Email email : emails = emailList) {
                emailRepository.save(email);
            }
            totalEmails = totalEmails + emails.size();
            accounts = createRandomAccountsForCustomer(customer, key_suffix);
            totalAccounts = totalAccounts + accounts.size();
            for (Account account : accounts) {
                accountRepository.save(account);
            }
            customerRepository.save(customer);
            if (!accounts.isEmpty()) {
                allAccounts.addAll(accounts);
            }
        }

        custTimer.end();
        log.info("Customers=" + noOfCustomers + " Accounts=" + totalAccounts +
                " Emails=" + totalEmails + " Phones=" + totalPhone + " in " +
                custTimer.getTimeTakenSeconds() + " secs");
        return allAccounts;
    }

    /**
     * Creates a random transaction with some skew for some accounts.
     * @return
     */
    @Override
    public void createItemsAndAmount(int noOfItems, Transaction transaction) {
        Map<String, Double> items = new HashMap<String, Double>();
        double totalAmount = 0;

        for (int i = 0; i < noOfItems; i++) {

            double amount = Math.random() * 100;
            items.put("item" + i, amount);

            totalAmount += amount;
        }
        transaction.setAmount(totalAmount);
        transaction.setOriginalAmount(totalAmount);
    }


    public static class Timer {

        private long timeTaken;
        private long start;

        public Timer(){
            start();
        }
        public void start(){
            this.start = System.currentTimeMillis();
        }
        public void end(){
            this.timeTaken = System.currentTimeMillis() - start;
        }

        public long getTimeTakenMillis(){
            return this.timeTaken;
        }

        public int getTimeTakenSeconds(){
            double v = (double) this.timeTaken / 1000;
            return ((int) v);
        }

        public String getTimeTakenMinutes(){
            double v = (double) this.timeTaken / (1000*60);
            return String.format("%1$,.2f", v);
        }

    }

    public static List<String> locations = Arrays.asList("Chicago", "Minneapolis", "St. Paul", "Plymouth", "Edina",
            "Duluth", "Bloomington", "Bloomington", "Rockford", "Champaign");

    public static String[] transactionStatus = {"POSTED", "AUTHORIZED", "SETTLED", "POSTED", "POSTED", "POSTED",
            "POSTED", "POSTED", "POSTED", "POSTED", "POSTED", "POSTED",
            "POSTED", "POSTED", "POSTED", "POSTED", "POSTED", "POSTED",
            "AUTHORIZED", "SETTLED","POSTED","AUTHORIZED","SETTLED","POSTED","POSTED","POSTED"};
    public static String[] States = {"IL", "MN", "MN", "MN", "MN","CA", "AZ", "AL", "AK", "TX", "WY", "PR",
            "MN", "IL", "MN", "MN", "IL", "IA", "WI", "SD", "ND", "MD", "CT", "WI", "KS", "IN","DE","TN"
    };

    public static String[] genderList = {"M", "F", "F", "M", "F", "F", "M", "M", "M", "F"};

    public static String[] issuers = {"Tesco", "Sainsbury", "Wal-Mart Stores", "Morrisons",
            "Marks & Spencer", "Walmart", "John Lewis", "Cub Foods", "Argos", "Co-op", "Currys", "PC World", "B&Q",
            "Somerfield", "Next", "Spar", "Amazon", "Costa", "Starbucks", "BestBuy", "Lowes", "BarnesNoble",
            "Carlson Wagonlit Travel", "Pizza Hut", "Local Pub"};

    public static String[] issuersCD = {"5411", "5411", "5310", "5499",
            "5310", "5912", "5311", "5411", "5961", "5300", "5732", "5964", "5719",
            "5411", "5651", "5411", "5310", "5691", "5814", "5732", "5211", "5942", "5962",
            "5814", "5813"};

    public static String[] issuersCDdesc = {"Grocery Stores", "Grocery Stores",
            "Discount Stores", "Misc Food Stores Convenience Stores and Specialty Markets",
            "Discount Stores", "Drug Stores and Pharmacies", "Department Stores", "Supermarkets", "Mail Order Houses",
            "Wholesale Clubs", "Electronic Sales",
            "Direct Marketing Catalog Merchant", "Miscellaneous Home Furnishing Specialty Stores",
            "Grocery Stores", "Family Clothing Stores", "Grocery Stores", "Discount Stores",
            "Mens and Womens Clothing Stores",
            "Fast Food Restaurants",
            "Electronic Sales",
            "Lumber and Building Materials Stores",
            "Book Stores", "Direct Marketing Travel Related Services",
            "Fast Food Restaurants", "Drinking Places, Bars, Taverns, Cocktail lounges, Nightclubs and Discos"};


    public static List<String> notes = Arrays.asList("Shopping", "Shopping", "Shopping", "Shopping", "Shopping",
            "Pharmacy", "HouseHold", "Shopping", "Household", "Shopping", "Tech", "Tech", "Diy", "Shopping", "Clothes",
            "Shopping", "Amazon", "Coffee", "Coffee", "Tech", "Diy", "Travel", "Travel", "Eating out", "Eating out");

    public static List<String> tagList = Arrays.asList("Home", "Home", "Home", "Home", "Home", "Home", "Home", "Home",
            "Work", "Work", "Work", "Home", "Home", "Home", "Work", "Work", "Home", "Work", "Work", "Work", "Work",
            "Work", "Work", "Work", "Work", "Expenses", "Luxury", "Entertaining", "School");

}
