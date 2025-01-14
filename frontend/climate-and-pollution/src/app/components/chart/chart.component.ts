import { Country, WhoCountries } from './../../models/countries.interface'
import {
  Component,
  AfterViewInit,
  ViewChild,
  ElementRef,
  inject,
} from '@angular/core'
import { Chart, registerables } from 'chart.js'
import { ApiService } from './../../services/api.service'
import {
  catchError,
  combineLatest,
  map,
  Observable,
  Subject,
  takeUntil,
  of,
} from 'rxjs'
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { YearlyAirPollutionData } from '../../models/yearly-air-pollutions-data'

import { MatInputModule } from '@angular/material/input'
import { MatButtonModule } from '@angular/material/button'
import { MatSnackBar } from '@angular/material/snack-bar'
import { MatSliderModule } from '@angular/material/slider'
import { ChartDataModel } from '../../models/chart-data.interface'
import { ChartDataApiResponse } from '../../models/combined-api-call.interface'
import Settings from '../../models/settings.interface'
import { chartFilterOptions } from '../../utils/filter-options'

@Component({
  selector: 'app-chart',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatSliderModule,
  ],
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss'],
})
export class ChartComponent implements AfterViewInit {
  @ViewChild('climateAndPollutionChart', { static: true })
  canvasRef!: ElementRef<HTMLCanvasElement>
  private ctx!: CanvasRenderingContext2D
  private chartInstance: Chart | null = null

  private destroy$ = new Subject<void>()
  private _snackBar = inject(MatSnackBar)

  searchData: YearlyAirPollutionData | null = null
  filterOptions = chartFilterOptions

  sliderLow = 1990
  sliderHigh = 2024

  searchForm = new FormGroup({
    searchTerm: new FormControl('', [Validators.required]),
  })

  filterForm = new FormGroup({
    air15: new FormControl(true, { nonNullable: true }),
    air43: new FormControl(true, { nonNullable: true }),
    air41: new FormControl(true, { nonNullable: true }),
    air35: new FormControl(true, { nonNullable: true }),
    air11: new FormControl(true, { nonNullable: true }),
    cco2: new FormControl(true, { nonNullable: true }),
    sdgpm25: new FormControl(true, { nonNullable: true }),
    sdgairboda: new FormControl(true, { nonNullable: true }),
    poppollutingfuels: new FormControl(true, { nonNullable: true }),
    mortality: new FormControl(true, { nonNullable: true }),
    xTemp: new FormControl(true, { nonNullable: true }),
    drought: new FormControl(true, { nonNullable: true }),
  })

  constructor(private apiService: ApiService) {
    Chart.register(...registerables)
  }

  ngAfterViewInit(): void {
    this.ctx = this.canvasRef.nativeElement.getContext('2d')!
    if (!this.ctx) {
      console.error('Canvas element not found')
    } else {
      this.loadSettingsFromLocalStorage()
    }
  }

  saveSettings() {
    const settings: Settings = {
      chart: {
        country: this.searchForm.value.searchTerm!,
        filters: this.filterForm.getRawValue(),
        yearRange: { low: this.sliderLow, high: this.sliderHigh },
      },
    }

    // Saves chart information
    this.apiService
      .saveSettings(settings, 'chart')
      .pipe(
        catchError((error) => {
          console.error('Failed to save settings', error)
          this.showError('Error occured while saving settings.')
          return of({}) // Return an empty settings object.
        })
      )
      .subscribe(() => {
        console.log('Chart settings saved: ', settings)
      })
  }

  // Loads settings from backend and sets relevant values in the component
  private loadSettingsFromLocalStorage() {
    // Loads chart information
    this.apiService
      .loadSettings('chart')
      .pipe(
        catchError((error) => {
          console.error('Failed to load settings', error)
          this.showError('Error occured while loading settings.')
          return of({}) // Return an empty settings object.
        })
      )
      .subscribe({
        next: (settings: Settings) => {
          if (Object.keys(settings).length > 0) {
            this.searchForm.setValue({
              searchTerm: settings.chart?.country || 'Finland',
            })

            const filters = settings.chart?.filters || {}

            this.filterForm.setValue({
              air15: filters.air15 ?? true,
              air43: filters.air43 ?? true,
              air41: filters.air41 ?? true,
              cco2: filters.cco2 ?? true,
              air35: filters.air35 ?? true,
              air11: filters.air11 ?? true,
              sdgpm25: filters.sdgpm25 ?? true,
              sdgairboda: filters.sdgairboda ?? true,
              poppollutingfuels: filters.poppollutingfuels ?? true,
              mortality: filters.mortality ?? true,
              xTemp: filters.xTemp ?? true,
              drought: filters.drought ?? true,
            })

            this.sliderLow = settings.chart?.yearRange?.low ?? 1990
            this.sliderHigh = settings.chart?.yearRange?.high ?? 2024
          }
          this.searchCountry()
        },
      })
  }

  searchCountry() {
    const searchTerm = this.searchForm.value.searchTerm
    if (searchTerm) {
      this.apiService.getCountries().subscribe((data: WhoCountries) => {
        const searchedCountry: Country = Object(data).value.find(
          (country: Country) =>
            country.Title.toLowerCase() === searchTerm.toLowerCase() ||
            country.Code.toLowerCase() === searchTerm.toLowerCase()
        )
        if (searchedCountry) {
          this.loadDataAndCreateChart({
            title: searchedCountry.Title,
            code: searchedCountry.Code,
          })
        } else {
          this.showError(`Cannot find a country with search: ${searchTerm}`)
        }
      })
    } else {
      this.loadDataAndCreateChart({
        title: 'Finland',
        code: 'FIN',
      })
    }
  }

  showError(message: string) {
    this._snackBar.open(message, 'Close')
  }

  loadDataAndCreateChart(country: { title: string; code: string }) {
    // Get selected indicators
    const selectedIndicator = this.filterForm.value

    const apiCalls: ChartDataApiResponse = {}

    // Dynamically add API calls based on selected filters
    if (selectedIndicator.air15) {
      apiCalls['air15'] = this.apiService
        .getAir15(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.air43) {
      apiCalls['air43'] = this.apiService
        .getAir43(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.air41) {
      apiCalls['air41'] = this.apiService
        .getAir41(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.cco2) {
      apiCalls['cco2'] = this.apiService
        .getCCO2(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.air35) {
      apiCalls['air35'] = this.apiService
        .getAir35(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.air11) {
      apiCalls['air11'] = this.apiService
        .getAir11(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.sdgpm25) {
      apiCalls['sdgpm25'] = this.apiService
        .getSDGPM25(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.sdgairboda) {
      apiCalls['sdgairboda'] = this.apiService
        .getSDGAirBODA(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.poppollutingfuels) {
      apiCalls['poppollutingfuels'] = this.apiService
        .getPopPollutingFuels(country.code, this.sliderLow, this.sliderHigh)
        .pipe(map((data) => data || null))
    }
    if (selectedIndicator.mortality) {
      apiCalls['mortality'] = this.apiService.getMortality(country.code).pipe(
        map((data) => data || null),
        catchError((err) => {
          if (err.status === 503) {
            this.showError('Rate limit exeeded for API: Mortality')
          }
          return of([null])
        })
      )
    }
    if (selectedIndicator.xTemp) {
      apiCalls['xTemp'] = this.apiService.getXTemp(country.code).pipe(
        map((data) => data || null),
        catchError((err) => {
          if (err.status === 503) {
            this.showError('Rate limit exeeded for API: Extreme Temperature')
          }
          return of([null])
        })
      )
    }
    if (selectedIndicator.drought) {
      apiCalls['drought'] = this.apiService.getDrought(country.code).pipe(
        map((data) => data || null),
        catchError((err) => {
          if (err.status === 503) {
            this.showError('Rate limit exeeded for API: Drought')
          }
          return of([null])
        })
      )
    }

    if (Object.keys(apiCalls).length > 0) {
      combineLatest(
        apiCalls as Record<string, Observable<(ChartDataModel | null)[]>>
      )
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: (result: Record<string, (ChartDataModel | null)[]>) => {
            const datasets = Object.keys(result)
              .map((key) => {
                const data = result[key] || null
                if (data) {
                  const label = this.filterOptions.find(
                    (option) => option.key == key
                  )?.label
                  return this.createDataset(label ?? `Label for ${key}`, data)
                }
                return null
              })
              .filter(Boolean)

            // Creates the chart using the values
            this.createChart(this.ctx, datasets)
            this.chartInstance?.update()
          },
          error: (err) => console.error('Failed to load data', err),
        })
    } else {
      this.showError('No APIs selected; no calls made.')
    }
  }

  // General method as a base for every data
  createDataset(label: string, data: (ChartDataModel | null)[]) {
    return {
      label: label,
      data: data,
      fill: false,
      tension: 0.1,
    }
  }

  // Creates the chart with the values and year
  createChart(ctx: CanvasRenderingContext2D, datasets: any[]) {
    if (this.chartInstance) {
      this.chartInstance.destroy()
    }
    this.chartInstance = new Chart(ctx, {
      type: 'line',
      data: {
        datasets: datasets,
      },
      options: {
        responsive: true,
        scales: {
          y: {
            type: 'linear',
            title: {
              display: true,
              text: 'Values',
            },
          },
          x: {
            type: 'linear',
            title: {
              display: true,
              text: 'Year',
            },
            ticks: {
              callback: (value) => {
                return Math.floor(Number(value))
              },
            },
          },
        },
      },
    })
  }
}
