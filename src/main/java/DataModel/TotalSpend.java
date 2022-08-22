package DataModel;

public class TotalSpend {
        float total;
        String email;
        public TotalSpend(String email, float total){
            this.email = email;
            this.total = total;
        }
        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
         public String getEmail() {
            return email;
         }

        public void setEmail(String email) {
            this.email = email;
         }

        @Override
        public String toString() {
            return "{" +
                    "email: " + email + '\'' +
                    ", total : " + total +
                    '}';
        }
}
