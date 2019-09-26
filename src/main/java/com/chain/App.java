package com.chain;

import com.chain.erc721.Asset;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class App {
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(1_000_000_000L);
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1_000_000_000L);
    private static final StaticGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static Web3j web3j = Web3j.build(new HttpService()); // default if localhost:8545

    @Value("${account.keystore.password}")
    private String password;
    @Value("${account.keystore}")
    private String keystore;

    private void mintWithTokenURI() {
        String contractAddress = null;
        Credentials credentials = loadCredentials(password, keystore);
        TransactionManager rawTransactionManager = new RawTransactionManager(web3j, credentials, 60, 300);
        Asset contract = Asset.load(contractAddress, web3j, rawTransactionManager, gasProvider);
        try {
            contract.mintWithTokenURI("0xed1bb8e79489ec8e30b4ec133054164bba650f06", BigInteger.valueOf(1), "brand:xxx,color:red,owner:Philips");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Credentials loadCredentials(String password, String keystore) {
        try {
            WalletFile walletFile = objectMapper.readValue(keystore, WalletFile.class);
            Credentials credentials = Credentials.create(Wallet.decrypt(password, walletFile));
            return credentials;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
