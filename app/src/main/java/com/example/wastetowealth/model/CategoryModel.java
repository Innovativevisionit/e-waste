package com.example.wastetowealth.model;

public class CategoryModel {
        private String name;
        private String status;

        public CategoryModel(String name, String status) {
            this.name = name;
            this.status = status;
        }

        public CategoryModel() {
        }

        public String getCategory() {
            return name;
        }

        public void setCategory(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
