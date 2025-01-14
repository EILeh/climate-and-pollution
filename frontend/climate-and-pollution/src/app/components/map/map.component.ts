import { ApiService } from './../../services/api.service'
import { Component, AfterViewInit, inject } from '@angular/core'
import * as L from 'leaflet'
import { MarkerService } from '../../services/marker.service'
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { MatInputModule } from '@angular/material/input'
import { MatButtonModule } from '@angular/material/button'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Pollution } from '../../models/pollution.interface'
import { catchError, of, Subject, takeUntil } from 'rxjs'
import { mapFilterOptions } from '../../utils/filter-options'
import Settings from '../../models/settings.interface'

const iconRetinaUrl = 'assets/marker-icon-2x.png'
const iconUrl = 'assets/marker-icon.png'
const shadowUrl = 'assets/marker-shadow.png'
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41],
})
L.Marker.prototype.options.icon = iconDefault

@Component({
  selector: 'app-map',
  standalone: true,
  imports: [MatInputModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './map.component.html',
  styleUrl: './map.component.scss',
})
export class MapComponent implements AfterViewInit {
  private destroy$ = new Subject<void>()
  private map: L.Map | undefined
  private _snackBar = inject(MatSnackBar)

  searchData: Pollution | null = null
  searchForm = new FormGroup({
    searchTerm: new FormControl('', [Validators.required]),
  })
  filterForm = new FormGroup({
    co: new FormControl(true, { nonNullable: true }),
    no: new FormControl(true, { nonNullable: true }),
    no2: new FormControl(true, { nonNullable: true }),
    o3: new FormControl(true, { nonNullable: true }),
    so2: new FormControl(true, { nonNullable: true }),
    pm2_5: new FormControl(true, { nonNullable: true }),
    pm10: new FormControl(true, { nonNullable: true }),
    nh3: new FormControl(true, { nonNullable: true }),
  })
  filterOptions = mapFilterOptions

  constructor(
    private markerService: MarkerService,
    private apiService: ApiService
  ) {
    this.filterForm.valueChanges
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.updateMapMarkers()
      })
  }

  ngAfterViewInit(): void {
    this.loadSettingsFromLocalStorage()
  }

  // Initializes map
  private initMap(): void {
    this.map = L.map('map')

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(this.map)
  }

  // Sets map location according to searched city and selected filter values
  private updateMapLocation(data: Pollution) {
    if (this.map) {
      this.map.setView([data.coord.lat, data.coord.lon], 13)
      this.markerService.createMarker(
        this.map,
        data,
        this.filterForm.getRawValue()
      )
    }
  }

  // Returns Observable of the searched city
  searchCity(city: string) {
    const result = this.apiService.getPollution(city)
    // Subscribes to observable
    result
      .pipe(
        takeUntil(this.destroy$),
        catchError((error) => {
          console.error('Error fetching pollution data:', error)
          this.showError(`Cannot find a country with search: ${city}`)
          return of(null) // Return a fallback value or null.
        })
      )
      .subscribe((response: Pollution | null) => {
        if (response) {
          this.searchData = response
          this.updateMapLocation(response)
        }
      })
  }

  //Updates map markers based on selected pollution values
  private updateMapMarkers() {
    if (this.map && this.searchData) {
      // Clear existing markers
      this.map.eachLayer((layer) => {
        if (layer instanceof L.Marker) {
          this.map?.removeLayer(layer)
        }
      })

      // Use stored data to create markers with current filters
      this.markerService.createMarker(
        this.map,
        this.searchData,
        this.filterForm.getRawValue()
      )
    }
  }

  saveSettings() {
    if (this.searchForm.valid) {
      const settings: Settings = {
        map: {
          city: this.searchForm.value.searchTerm ?? '',
          filters: this.filterForm.getRawValue(),
        },
      }
      this.apiService
        .saveSettings(settings, 'map')
        .pipe(
          catchError((error) => {
            console.error('Failed to save settings', error)
            this.showError('Error occured while saving settings.')
            return of({}) // Return an empty settings object.
          })
        )
        .subscribe(() => {
          console.log('Settings saved:', settings)
        })
    } else {
      this.showError('Please enter a valid city name.')
    }
  }

  private loadSettingsFromLocalStorage() {
    this.apiService
      .loadSettings('map')
      .pipe(
        catchError((error) => {
          console.error('Failed to load settings', error)
          this.showError('Error occured while loading settings.')
          return of({}) // Return an empty settings object.
        })
      )
      .subscribe({
        next: (settings: Settings) => {
          if (Object.keys(settings).length > 0 && settings.map) {
            this.searchForm.setValue({ searchTerm: settings.map?.city || '' })

            const filters = settings?.map?.filters ?? {}

            this.filterForm.setValue({
              co: filters.co ?? true,
              no: filters.no ?? true,
              no2: filters.no2 ?? true,
              o3: filters.o3 ?? true,
              so2: filters.so2 ?? true,
              pm2_5: filters.pm2_5 ?? true,
              pm10: filters.pm10 ?? true,
              nh3: filters.nh3 ?? true,
            })
            if (!this.map) {
              this.initMap()
            }
            this.updateMapMarkers()
            if (settings.map?.city) {
              this.searchCity(settings.map.city)
            }
          } else {
            this.initMap()
            this.searchCity('Tampere')
          }
        },
      })
  }

  showError(message: string) {
    this._snackBar.open(message, 'Close')
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }
}
