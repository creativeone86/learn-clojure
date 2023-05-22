(ns lein-app.car-budget)
;; --- SPECS ---

; v1
; we have 3 different brands with different prices
; we have a coupon with % discount
; let the user input its budget and coupon
; 1. print out info if the coupon is valid
; 2. find budget match (with applied coupon discount if applicable)

;; v2
; 1. support array of coupons
; 2. support price ranges for brand

(def cars [
           {:name "bmw" :price 60000}
           {:name "ferarri" :price 100000}
           {:name "fiat" :price 20000}])

(def coupon {:code "my_coupon" :percentage 20})

(defn carPrice
  [price discount] (if (nil? discount)
                      (float price)
                      (float (- price (* price (/ discount 100)))))
  )

(defn getDiscount
  [code]
  (if (= (:code coupon) code)
    (:percentage coupon))
  )

(defn main
  [budget code]
  (def discount (getDiscount code))
  (if (nil? discount)
    (println "COUPON NOT VALID\n")
    (println "COUPON VALID\n"))
  (doseq
    [car cars]
    (do
       (def price (carPrice (:price car) discount))
       (if (<= price budget)
         (println (:name car) "\t" price  )))
  ))
