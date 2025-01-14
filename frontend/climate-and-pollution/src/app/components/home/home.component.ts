import { Component } from '@angular/core'
import { MatCardModule } from '@angular/material/card'
import {
  ProgressSpinnerMode,
  MatProgressSpinnerModule,
} from '@angular/material/progress-spinner'
import { RouterLink } from '@angular/router'
@Component({
  selector: 'app-home',
  standalone: true,
  imports: [MatCardModule, MatProgressSpinnerModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  mode: ProgressSpinnerMode = 'determinate'
  value = (209 / 301) * 100
}
