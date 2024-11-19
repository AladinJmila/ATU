import { Component } from '@angular/core';
import { CountyDetailsComponent } from '../county-details/county-details.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-county',
  standalone: true,
  imports: [CountyDetailsComponent, FormsModule, CommonModule],
  templateUrl: './county.component.html',
  styleUrl: './county.component.css',
})
export class CountyComponent {
  g: string = 'Galway';
  lm: string = 'Leitrim';
  name: string = 'yo';
  names: string[] = ['yo', 'yo2', 'yo3'];
  newName: string = '';
  hidden: boolean = true;

  dblClickCounter: number = 0;
  titleInforString: string = `You double clicked this ${this.dblClickCounter} times`;

  addName() {
    this.names.push(this.newName);
    this.newName = '';
    this.hidden = !this.hidden;
  }

  doubleClick() {
    this.dblClickCounter++;
    this.titleInforString = `You double clicked this ${this.dblClickCounter} times`;

    // alert('You double clicked me!');
  }
}
