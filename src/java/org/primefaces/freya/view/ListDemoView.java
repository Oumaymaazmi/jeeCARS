/**
 *  Copyright 2009-2020 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.freya.view;

import bean.Car;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.freya.domain.Product;
import org.primefaces.freya.service.ProductService;

import org.primefaces.model.DualListModel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import service.CarFacade;
import service.ModeleFacade;

@Named
@RequestScoped
public class ListDemoView {

    private List<Car> cars;
    @EJB
    private CarFacade carFacade;

    private DualListModel<String> cities1;

    private List<String> cities2;

    private List<Product> products;

  
    
    private double price=65.00;

    @PostConstruct
    public void init() {

        this.cars = this.carFacade.findAll();
        System.out.println("caaaars " +cars.size());

    }

    public DualListModel<String> getCities1() {
        return cities1;
    }

    public List<String> getCities2() {
        return cities2;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public CarFacade getCarFacade() {
        return carFacade;
    }

    public void setCarFacade(CarFacade carFacade) {
        this.carFacade = carFacade;
    }
    
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
