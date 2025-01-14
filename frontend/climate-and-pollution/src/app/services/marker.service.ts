import { Injectable } from '@angular/core'
import * as L from 'leaflet'
import { PopupService } from './popup.service'
import { Pollution } from '../models/pollution.interface'
import { MapFilters } from '../models/map-filters.interface'

@Injectable({
  providedIn: 'root',
})
export class MarkerService {
  constructor(private popupService: PopupService) {}

  createMarker(map: L.Map, data: Pollution, filters: MapFilters) {
    const marker = L.marker([data.coord.lat, data.coord.lon])

    marker.bindPopup(this.popupService.createPopup(data, filters))
    marker.addTo(map)
  }
}
