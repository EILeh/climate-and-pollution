import { Routes } from '@angular/router'
import { HomeComponent } from './components/home/home.component'
import { MapComponent } from './components/map/map.component'
import { ChartComponent } from './components/chart/chart.component'
import { NotFoundComponent } from './components/not-found/not-found.component'

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'map', component: MapComponent },
  { path: 'chart', component: ChartComponent },
  { path: '**', component: NotFoundComponent },
]
