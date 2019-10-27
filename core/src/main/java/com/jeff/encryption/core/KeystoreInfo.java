package com.jeff.encryption.core;

/**
 * @author Jeff
 * @describe
 * @date 2019/10/24.
 */
public class KeystoreInfo {
    private String keystoreName;
    private String keystorePassword;
    private String alias;
    private String aliasPassword;

    private KeystoreInfo(String keystoreName, String keystorePassword, String alias, String aliasPassword) {
        this.keystoreName = keystoreName;
        this.keystorePassword = keystorePassword;
        this.alias = alias;
        this.aliasPassword = aliasPassword;
    }

    public String getKeystoreName() {
        return keystoreName;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public String getAlias() {
        return alias;
    }

    public String getAliasPassword() {
        return aliasPassword;
    }

    public static class Builder {
        private String keystoreName;
        private String keystorePassword;
        private String alias;
        private String aliasPassword;

        public Builder() {
        }

        public Builder(String keystoreName, String keystorePassword, String alias, String aliasPassword) {
            this.keystoreName = keystoreName;
            this.keystorePassword = keystorePassword;
            this.alias = alias;
            this.aliasPassword = aliasPassword;
        }

        public String getKeystoreName() {
            return keystoreName;
        }

        public Builder setKeystoreName(String keystoreName) {
            this.keystoreName = keystoreName;
            return this;
        }

        public String getKeystorePassword() {
            return keystorePassword;
        }

        public Builder setKeystorePassword(String keystorePassword) {
            this.keystorePassword = keystorePassword;
            return this;
        }

        public String getAlias() {
            return alias;
        }

        public Builder setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public String getAliasPassword() {
            return aliasPassword;
        }

        public Builder setAliasPassword(String aliasPassword) {
            this.aliasPassword = aliasPassword;
            return this;
        }

        public KeystoreInfo create() {
            return new KeystoreInfo(this.keystoreName, this.keystorePassword, this.alias, this.aliasPassword);
        }
    }
}
