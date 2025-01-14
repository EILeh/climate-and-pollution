import { Injectable } from '@angular/core'
import * as L from 'leaflet'
import { Pollution } from '../models/pollution.interface'
import { MapFilters } from '../models/map-filters.interface'

@Injectable({
  providedIn: 'root',
})
export class PopupService {
  constructor() {}

  createPopup(data: Pollution, filters: MapFilters): string {
    let popupContent = `
      <div>Country: ${data.country}</div>
      <div>City: ${data.city}</div>
      <div>Air Quality Index (AQI): ${data.list[0].main.aqi}</div>
      `
    if (Object.values(filters).some((val) => val === true))
      popupContent += `
      <strong>Pollutants:</strong><br>
      `

    // Include only selected pollutants based on filters
    if (filters.co && data.list[0].components.co !== undefined) {
      popupContent += `<div>CO: ${data.list[0].components.co} µg/m<sup>3</sup></div>`
    }
    if (filters.no && data.list[0].components.no !== undefined) {
      popupContent += `<div>NO: ${data.list[0].components.no} µg/m<sup>3</sup></div>`
    }
    if (filters.no2 && data.list[0].components.no2 !== undefined) {
      popupContent += `<div>NO<sub>2</sub>: ${data.list[0].components.no2} µg/m<sup>3</sup></div>`
    }
    if (filters.o3 && data.list[0].components.o3 !== undefined) {
      popupContent += `<div>O<sub>3</sub>: ${data.list[0].components.o3} µg/m<sup>3</sup></div>`
    }
    if (filters.so2 && data.list[0].components.so2 !== undefined) {
      popupContent += `<div>SO<sub>2</sub>: ${data.list[0].components.so2} µg/m<sup>3</sup></div>`
    }
    if (filters.pm2_5 && data.list[0].components.pm2_5 !== undefined) {
      popupContent += `<div>PM<sub>2.5</sub>: ${data.list[0].components.pm2_5} µg/m<sup>3</sup></div>`
    }
    if (filters.pm10 && data.list[0].components.pm10 !== undefined) {
      popupContent += `<div>PM<sub>10</sub>: ${data.list[0].components.pm10} µg/m<sup>3</sup></div>`
    }
    if (filters.nh3 && data.list[0].components.nh3 !== undefined) {
      popupContent += `<div>NH<sub>3</sub>: ${data.list[0].components.nh3} µg/m<sup>3</sup></div>`
    }

    return popupContent
  }
}
