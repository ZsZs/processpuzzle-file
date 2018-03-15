import { Directive, ElementRef } from '@angular/core';

@Directive({
  selector: '[sampleDirective]'
})
export class SampleDirective {

  /* tslint:disable-next-line */
  constructor(private el: ElementRef) {
  }

}
